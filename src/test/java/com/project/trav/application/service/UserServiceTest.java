package com.project.trav.application.service;

import com.project.trav.application.services.UserService;
import com.project.trav.domain.entity.Role;
import com.project.trav.domain.entity.Status;
import com.project.trav.domain.entity.User;
import com.project.trav.domain.repository.UserRepository;
import com.project.trav.exeption.EntityAlreadyExists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    void getUsers(){
        var userList = Arrays.asList(
                new User().setId(1L).setName("Ivan").setSurname("Baranetskyi").setMail("baranetskiy@gmail.com")
                        .setPhone("+380956954604").setLogin("kreker").setPassword("admin").setRole(Role.USER).setStatus(Status.ACTIVE)
                        .setTickets(new ArrayList<>()),
                new User().setId(1L).setName("Ivan").setSurname("Baranetskyi").setMail("baranetskiy@gmail.com")
                        .setPhone("+380956954604").setLogin("kreker").setPassword("admin").setRole(Role.USER).setStatus(Status.ACTIVE)
                        .setTickets(new ArrayList<>()));
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        var expectedList = userService.getUsers();
        assertThat(expectedList).isEqualTo(userList);
    }
    @Test
    void getUser_success(){
        var sourceUser = new User().setId(1L).setName("Ivan").setSurname("Baranetskyi").setMail("baranetskiy@gmail.com")
                .setPhone("+380956954604").setLogin("kreker").setPassword("admin").setRole(Role.USER).setStatus(Status.ACTIVE)
                .setTickets(new ArrayList<>());
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(sourceUser));
        var expectedUser = userService.getUser(1L);
        assertThat(expectedUser).isEqualTo(sourceUser);
    }
    @Test
    void getUser_failure(){
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,()->userService.getUser(1L));
    }
    @Test
    void addUser_success(){
        var user =new User().setId(1L).setName("Ivan").setSurname("Baranetskyi").setMail("baranetskiy@gmail.com")
                .setPhone("+380956954604").setLogin("kreker").setPassword("admin").setRole(Role.USER).setStatus(Status.ACTIVE)
                .setTickets(new ArrayList<>());
        Mockito.when(userRepository.existsUserByLoginOrMail(user.getLogin(), user.getMail())).thenReturn(false);
        userService.addUser(user);
        Mockito.verify(userRepository).save(user);
    }
    @Test
    void addUser_foundByLoginOrMail_failure(){
        var user =new User().setId(1L).setName("Ivan").setSurname("Baranetskyi").setMail("baranetskiy@gmail.com")
                .setPhone("+380956954604").setLogin("kreker").setPassword("admin").setRole(Role.USER).setStatus(Status.ACTIVE)
                .setTickets(new ArrayList<>());
        Mockito.when(userRepository.existsUserByLoginOrMail(user.getLogin(), user.getMail())).thenReturn(true);
        String expectedMessage = "User already exists";
        String actualMessage = Assertions.assertThrows(EntityAlreadyExists.class,()->
                userService.addUser(user)).getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
    @Test
    void updateUser_success(){
        var sourceUser =new User().setId(1L).setName("Ivan").setSurname("Baranetskyi").setMail("baranetskiy@gmail.com")
                .setPhone("+380956954604").setLogin("kreker").setPassword("admin").setRole(Role.USER).setStatus(Status.ACTIVE)
                .setTickets(new ArrayList<>());
        var expectedUser = new User().setId(1L).setName("Ivan").setSurname("Baranetskyi").setMail("baranetskiy@gmail.com")
                .setPhone("+380956954604").setLogin("kreker").setPassword("admin").setRole(Role.USER).setStatus(Status.ACTIVE)
                .setTickets(new ArrayList<>());

        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userRepository.existsUserByLoginOrMail(sourceUser.getLogin(), sourceUser.getMail())).thenReturn(false);
        userService.updateUser(sourceUser,1L);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue()).isEqualTo(expectedUser);
    }
    @Test
    void updateUser_foundById_failure(){
        var user =new User().setId(1L).setName("Ivan").setSurname("Baranetskyi").setMail("baranetskiy@gmail.com")
                .setPhone("+380956954604").setLogin("kreker").setPassword("admin").setRole(Role.USER).setStatus(Status.ACTIVE)
                .setTickets(new ArrayList<>());

        Mockito.when(userRepository.existsById(1L)).thenReturn(false);
        String expectedMessage = "User was not found";
        String actualMessage = Assertions.assertThrows(EntityNotFoundException.class,()->
                userService.updateUser(user,1L)).getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
    @Test
    void updateUser_foundByLoginOrMail_failure(){
        var user =new User().setId(1L).setName("Ivan").setSurname("Baranetskyi").setMail("baranetskiy@gmail.com")
                .setPhone("+380956954604").setLogin("kreker").setPassword("admin").setRole(Role.USER).setStatus(Status.ACTIVE)
                .setTickets(new ArrayList<>());

        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userRepository.existsUserByLoginOrMail(user.getLogin(), user.getMail())).thenReturn(true);
        String expectedMessage = "User already exists";
        String actualMessage = Assertions.assertThrows(EntityAlreadyExists.class,()->
                userService.updateUser(user,1L)).getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
    @Test
    void deleteUser_success(){
        Long id = 1L;
        Mockito.when(userRepository.existsById(id)).thenReturn(true);
        userService.deleteUser(id);
        Mockito.verify(userRepository).deleteById(id);
    }
    @Test
    void deleteUser_failure(){
        Mockito.when(userRepository.existsById(1L)).thenReturn(false);
        String expectedMessage = "User was not found";
        String actualMessage = Assertions.assertThrows(EntityNotFoundException.class,()->
                userService.deleteUser(1L)).getMessage();
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }

}

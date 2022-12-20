package com.project.trav.service;

import com.project.trav.mapper.UserMapper;
import com.project.trav.model.dto.UserDto;
import com.project.trav.model.dto.UserSaveRequest;
import com.project.trav.model.dto.UserUpdateRequest;
import com.project.trav.model.entity.Role;
import com.project.trav.model.entity.Status;
import com.project.trav.model.entity.User;
import com.project.trav.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
  private UserMapper userMapper;
  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private UserService userService;

  @Test
  void getUsers() {
    var userList = Arrays.asList(
        new UserDto().setId(1L).setName("Ivan").setSurname("Baranetskyi")
            .setMail("baranetskiy@gmail.com")
            .setPhone("+380956954604").setLogin("kreker").setPassword("admin").setRole(Role.USER)
            .setStatus(Status.ACTIVE)
            .setTicketsDto(new ArrayList<>()),
        new UserDto().setId(1L).setName("Ivan").setSurname("Baranetskyi")
            .setMail("baranetskiy@gmail.com")
            .setPhone("+380956954604").setLogin("kreker").setPassword("admin").setRole(Role.USER)
            .setStatus(Status.ACTIVE)
            .setTicketsDto(new ArrayList<>()));
    Mockito.when(userMapper.toUserDtos(Mockito.anyList())).thenReturn(userList);
    var expectedList = userService.getUsers();
    assertThat(expectedList).isEqualTo(userList);
  }

  @Test
  void getUser_success() {
    var sourceUser = new User().setId(1L).setName("Ivan").setSurname("Baranetskyi")
        .setMail("baranetskiy@gmail.com")
        .setPhone("+380956954604").setLogin("kreker").setPassword("admin").setRole(Role.USER)
        .setStatus(Status.ACTIVE)
        .setTickets(new ArrayList<>());
    Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(sourceUser));
    var expectedUser = userService.getUser(1L);
    assertThat(expectedUser).isEqualTo(userMapper.toUserDto(sourceUser));
  }

  @Test
  void getUser_failure() {
    Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

    Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUser(1L));
  }

  @Test
  void addUser() {
    var user = new UserSaveRequest().setName("Anton")
        .setSurname("Chaika")
        .setRole(Role.ADMIN)
        .setPassword("anton")
        .setStatus(Status.ACTIVE);
    user
        .setMail("anton@gmail.com")
        .setPhone("+1235456")
        .setMail("anton@gmail.com");
    Mockito.when(userRepository.findUserByLoginOrMailOrPhone(user.getLogin(), user.getMail(),
        user.getPhone())).thenReturn(List.of());
    userService.addUser(user);
    Mockito.verify(userRepository).save(Mockito.any(User.class));
  }

  @Test
  void updateUser_success() {
    var sourceUser = new User().setId(1L).setName("Ivan").setSurname("Baranetskyi")
        .setMail("baranetskiy@gmail.com")
        .setPhone("+380956954604").setLogin("kreker").setPassword("admin").setRole(Role.USER)
        .setStatus(Status.ACTIVE)
        .setTickets(null);
    var updateUser = new UserUpdateRequest().setName("Anton")
        .setSurname("Chaika")
        .setRole(Role.ADMIN)
        .setStatus(Status.ACTIVE);
    updateUser
        .setLogin("kreker")
        .setPhone("+1235456")
        .setMail("anton@gmail.com");
    Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(sourceUser));
    Mockito.when(userMapper.toUser(userService.getUser(1L))).thenReturn(sourceUser);
    userService.updateUser(updateUser, 1L);
    Mockito.verify(userRepository).save(sourceUser);
  }

  @Test
  void updateUser_failure() {
    var updateUser = new UserUpdateRequest().setName("Anton")
        .setSurname("Chaika")
        .setRole(Role.ADMIN)
        .setStatus(Status.ACTIVE);
    updateUser
        .setMail("anton@gmail.com")
        .setPhone("+1235456")
        .setMail("anton@gmail.com");
    Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
    String expectedMessage = "User was not found";
    String actualMessage = Assertions.assertThrows(EntityNotFoundException.class, () ->
        userService.updateUser(updateUser, 1L)).getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  void deleteUser_success() {
    var user = new User().setId(1L).setName("Ivan").setSurname("Baranetskyi")
        .setMail("baranetskiy@gmail.com")
        .setPhone("+380956954604").setLogin("kreker").setPassword("admin").setRole(Role.USER)
        .setStatus(Status.ACTIVE)
        .setTickets(new ArrayList<>());
    Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    userService.deleteUser(1L);
    Mockito.verify(userRepository).deleteById(1L);
  }

  @Test
  void deleteUser_failure() {
    Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
    String expectedMessage = "User was not found";
    String actualMessage = Assertions.assertThrows(EntityNotFoundException.class, () ->
        userService.deleteUser(1L)).getMessage();
    assertThat(expectedMessage).isEqualTo(actualMessage);
  }

}

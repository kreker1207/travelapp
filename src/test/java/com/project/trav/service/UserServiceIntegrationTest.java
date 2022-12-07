package com.project.trav.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.project.trav.TravelApplication;
import com.project.trav.mapper.UserMapper;
import com.project.trav.model.dto.UserDto;
import com.project.trav.model.dto.UserSaveRequest;
import com.project.trav.model.dto.UserUpdateRequest;
import com.project.trav.model.entity.Role;
import com.project.trav.model.entity.Status;
import com.project.trav.model.entity.User;

import com.project.trav.repository.UserH2Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = TravelApplication.class)
@ExtendWith(SpringExtension.class)
@Transactional
class UserServiceIntegrationTest {

  @Autowired
  private UserService userService;
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private UserH2Repository userH2Repository;

  @BeforeAll
  public static void setSecurityContext() {
    Set<SimpleGrantedAuthority> authority = Role.ADMIN.grantedAuthorities();
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken("admin", "admin", authority));
  }

  @AfterEach
  public void clearDataBase() {
    userH2Repository.deleteAll();
  }

  @Test
  void getUser() {
    User user = createAndSaveUser();
    assertEquals(1,userH2Repository.findAll().size());
    UserDto foundUser = userService.getUser(user.getId());
    assertEquals(userMapper.toUserDto(user), foundUser);
  }

  @Test
  @Sql(statements = "INSERT INTO users (name, surname, mail, phone, login, password, role, status) "
      + "VALUES ('Ivan','Baran','bara@gma.qew','+144568','kreker','1234','USER','ACTIVE')")
  @Sql(statements = "INSERT INTO users (name, surname, mail, phone, login, password, role, status) "
      + "VALUES ('Kiril','Anrushckenk','kiril@gma.qew','+144532','kirez','rrrr','USER','ACTIVE')")
  void getAllUsers() {
    List<UserDto> foundUser = userService.getUsers();
    assertEquals(2, userH2Repository.findAll().size());
    assertEquals(2, foundUser.size());
    assertEquals("Ivan", foundUser.get(0).getName());
  }

  @Test
  void addUser() {
    userService.addUser(new UserSaveRequest()
        .setName("Anton")
        .setSurname("Chaika")
        .setLogin("antonio")
        .setPassword("anton")
        .setMail("anton@gmail.com")
        .setPhone("+1235456")
        .setRole(Role.ADMIN)
        .setStatus(Status.ACTIVE));
    assertEquals(1, userH2Repository.findAll().size());
    assertEquals("Anton", userH2Repository.findById(1L).get().getName());
  }
  @Test
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (1,'Ivan','Baran','bara@gma.qew','+144568','kreker','1234','USER','ACTIVE')")
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (2,'Kiril','Anrushckenk','kiril@gma.qew','+144532','kirez','rrrr','USER','ACTIVE')")
  void deleteUser() {
    assertEquals(2, userService.getUsers().size());
    userService.deleteUser(1L);
    assertEquals(1, userService.getUsers().size());
  }

  @Test
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (1,'Ivan','Baran','bara@gma.qew','+144568','keeker','1234','USER','ACTIVE')")
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (2,'Kiril','Anrushckenk','kiril@gma.qew','+144532','kirez','rrrr','USER','ACTIVE')")
  void updateUser() {
    assertEquals("Ivan", userService.getUser(1L).getName());
    userService.updateUser(new UserUpdateRequest()
        .setName("Boris")
        .setSurname("Baran")
        .setLogin("keeker")
        .setMail("bara@gma.qew")
        .setRole(Role.USER)
        .setStatus(Status.ACTIVE)
        .setPhone("+144568"), 1L);
    assertEquals("Boris", userService.getUser(1L).getName());
  }

  @Test
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (1,'Ivan','Baran','bara@gma.qew','+144568','keeker','1234','USER','ACTIVE')")
  void deactivateUser() {
    assertEquals(Status.ACTIVE, userService.getUser(1L).getStatus());
    userService.deactivateUser(1L);
    assertEquals(Status.BANNED, userService.getUser(1L).getStatus());
  }
  private User createAndSaveUser() {
    User user = new User()
        .setId(1L)
        .setName("Anton")
        .setTickets(new ArrayList<>())
        .setSurname("Chaika")
        .setLogin("antonio")
        .setPassword("anton")
        .setMail("anton@gmail.com")
        .setPhone("+1235456")
        .setRole(Role.ADMIN)
        .setStatus(Status.ACTIVE);
    return userH2Repository.save(user);
  }
}

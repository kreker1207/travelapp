package com.project.trav.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.trav.TravelApplication;
import com.project.trav.exeption.EntityAlreadyExists;
import com.project.trav.exeption.EntityNotFoundByIdException;
import com.project.trav.mapper.UserMapper;
import com.project.trav.model.dto.UserDto;
import com.project.trav.model.dto.UserDto.Fields;
import com.project.trav.model.dto.UserSaveRequest;
import com.project.trav.model.dto.UserUpdateRequest;
import com.project.trav.model.entity.Role;
import com.project.trav.model.entity.Status;
import com.project.trav.model.entity.User;

import com.project.trav.repository.UserH2Repository;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

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

  @AfterEach
  public void clearDataBase() {
    userH2Repository.deleteAll();
  }

  @Test
  void getUser_success() {
    User user = createAndSaveUser();
    assertEquals(1, userH2Repository.findAll().size());
    UserDto foundUser = userService.getUser(user.getId());
    assertEquals(userMapper.toUserDto(user), foundUser);
  }

  @Test
  void getUserNotFound_fail() {
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      userService.getUser(1L);
    }, "User was not found");
    assertEquals("User was not found", thrown.getMessage());
  }

  @Test
  @Sql(statements = "INSERT INTO users (name, surname, mail, phone, login, password, role, status) "
      + "VALUES ('Ivan','Baran','bara@gma.qew','+144568','kreker','1234','USER','ACTIVE')")
  @Sql(statements = "INSERT INTO users (name, surname, mail, phone, login, password, role, status) "
      + "VALUES ('Kiril','Anrushckenk','kiril@gma.qew','+144532','kirez','rrrr','USER','ACTIVE')")
  void getAllUsers_success() {
    List<UserDto> foundUser = userService.getUsers();
    assertEquals(2, userH2Repository.findAll().size());
    assertEquals(2, foundUser.size());
    assertEquals("Ivan", foundUser.get(0).getName());
  }

  @Test
  void addUser_success() {
    var expectedUserDto = new UserDto().setName("Anton")
        .setSurname("Chaika")
        .setRole(Role.ADMIN)
        .setPassword("admin")
        .setStatus(Status.ACTIVE)
        .setLogin("kreker")
        .setPhone("+1235456")
        .setMail("anton@gmail.com");
    var userSaveRequestResult = userService.addUser(createUserSaveRequest());
    assertThat(userSaveRequestResult).usingRecursiveComparison()
        .ignoringFields(Fields.password, Fields.id).isEqualTo(expectedUserDto);
  }

  @Test
  @Sql(statements = "INSERT INTO users (name, surname, mail, phone, login, password, role, status) "
      + "VALUES ('Ivan','Baran','bara@gma.qew','+144568','kreker','1234','USER','ACTIVE')")
  void addUserUniqueLogin_fail() {
    var userSaveRequest = createUserSaveRequest();
    EntityAlreadyExists thrown = assertThrows(EntityAlreadyExists.class, () -> {
      userService.addUser(userSaveRequest);
    }, "User with this login/mail already exists");
    assertEquals("User with this login/mail already exists", thrown.getMessage());
  }

  @Test
  @Sql(statements = "INSERT INTO users (name, surname, mail, phone, login, password, role, status) "
      + "VALUES ('Ivan','Baran','bara@gma.qew','+1235456','baranoc','1234','USER','ACTIVE')")
  void addUserUniquePhone_fail() {
    var userSaveRequest = createUserSaveRequest();
    EntityAlreadyExists thrown = assertThrows(EntityAlreadyExists.class, () -> {
      userService.addUser(userSaveRequest);
    }, "User with this login/mail already exists");
    assertEquals("User with this login/mail already exists", thrown.getMessage());
  }

  @Test
  @Sql(statements = "INSERT INTO users (name, surname, mail, phone, login, password, role, status) "
      + "VALUES ('Ivan','Baran','anton@gmail.com','+144568','baranoc','1234','USER','ACTIVE')")
  void addUserUniqueMail_fail() {
    var userSaveRequest = createUserSaveRequest();
    EntityAlreadyExists thrown = assertThrows(EntityAlreadyExists.class, () -> {
      userService.addUser(userSaveRequest);
    }, "User with this login/mail already exists");
    assertEquals("User with this login/mail already exists", thrown.getMessage());
  }

  @Test
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (1,'Ivan','Baran','bara@gma.qew','+144568','kreker','1234','USER','ACTIVE')")
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (2,'Kiril','Anrushckenk','kiril@gma.qew','+144532','kirez','rrrr','USER','ACTIVE')")
  void deleteUser_success() {
    assertEquals(2, userService.getUsers().size());
    userService.deleteUser(1L);
    assertEquals(1, userService.getUsers().size());
  }

  @Test
  void deleteNotFoundUser_fail() {
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      userService.deleteUser(1L);
    }, "User was not found");
    assertEquals("User was not found", thrown.getMessage());
  }

  @Test
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (1,'Ivan','Baran','bara@gma.qew','+144568','keeker','admin','USER','ACTIVE')")
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (2,'Kiril','Anrushckenk','kiril@gma.qew','+144532','kirez','rrrr','USER','ACTIVE')")
  void updateUser_success() {
    var expectedUserDto = new UserDto().setId(1L).setName("Anton")
        .setSurname("Chaika")
        .setRole(Role.ADMIN)
        .setPassword("admin")
        .setStatus(Status.ACTIVE)
        .setLogin("kreker")
        .setPhone("+1235456")
        .setMail("anton@gmail.com");
    assertEquals("Ivan", userService.getUser(1L).getName());
    var userUpdateResult = userService.updateUser(createUserUpdateRequest(), 1L);
    assertThat(userUpdateResult).usingRecursiveComparison()
        .ignoringFields(Fields.password, Fields.ticketsDto).isEqualTo(expectedUserDto);
  }

  @Test
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (1,'Ivan','Baran','bara@gma.qew','+144568','keeker','1234','USER','ACTIVE')")
  void updateUserNotFound_fail() {
    var userSaveRequest = createUserUpdateRequest();
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      userService.updateUser(userSaveRequest, 2L);
    }, "User was not found");
    assertEquals("User was not found", thrown.getMessage());
  }

  @Test
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (1,'Kiril','Anrushckenk','kiril@gma.qew','+144532','kirez','rrrr','USER','ACTIVE')")
  @Sql(statements = "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
      + "VALUES (2,'Ivan','Baran','bara@gma.qew','+144568','kreker','1234','USER','ACTIVE')")
  void updateUserUniqueLogin_fail() {
    var userUpdateRequest = createUserUpdateRequest();
    EntityAlreadyExists thrown = assertThrows(EntityAlreadyExists.class, () -> {
      userService.updateUser(userUpdateRequest, 1L);
    }, "User with this login/mail already exists");
    assertEquals("User with this login/mail already exists", thrown.getMessage());
  }

  @Test
  @Sql(statements = "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
      + "VALUES (1,'Ivan','Baran','bara@gma.qew','+144568','keekert','1234','USER','ACTIVE')")
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (2,'Kiril','Anrushckenk','anton@gmail.com','+144532','kirez','rrrr','USER','ACTIVE')")
  void updateUserUniqueMail_fail() {
    var userUpdateRequest = createUserUpdateRequest();
    EntityAlreadyExists thrown = assertThrows(EntityAlreadyExists.class, () -> {
      userService.updateUser(userUpdateRequest, 1L);
    }, "User with this login/mail already exists");
    assertEquals("User with this login/mail already exists", thrown.getMessage());
  }

  @Test
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (1,'Anton','Chaika','anton12@gmail.com','+144568','kreker12','1234','USER','ACTIVE')")
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (2,'Kiril','Anrushckenk','kiril@gma.qew','+1235456','kirez','rrrr','USER','ACTIVE')")
  void updateUserUniquePhone_fail() {
    var userUpdateRequest = createUserUpdateRequest();
    EntityAlreadyExists thrown = assertThrows(EntityAlreadyExists.class, () -> {
      userService.updateUser(userUpdateRequest, 1L);
    }, "User with this login/mail already exists");
    assertEquals("User with this login/mail already exists", thrown.getMessage());
  }

  @Test
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (1,'Ivan','Baran','bara@gma.qew','+144568','keeker','1234','USER','ACTIVE')")
  void deactivateUser_success() {
    assertEquals(Status.ACTIVE, userService.getUser(1L).getStatus());
    userService.deactivateUser(1L);
    assertEquals(Status.BANNED, userService.getUser(1L).getStatus());
  }

  private UserSaveRequest createUserSaveRequest() {
    var userSaveRequest = new UserSaveRequest().setName("Anton")
        .setSurname("Chaika")
        .setRole(Role.ADMIN)
        .setPassword("admin")
        .setStatus(Status.ACTIVE);
    userSaveRequest
        .setLogin("kreker")
        .setPhone("+1235456")
        .setMail("anton@gmail.com");
    return userSaveRequest;
  }

  private UserUpdateRequest createUserUpdateRequest() {
    var userUpdateRequest = new UserUpdateRequest()
        .setName("Anton")
        .setSurname("Chaika")
        .setRole(Role.ADMIN)
        .setStatus(Status.ACTIVE);
    userUpdateRequest
        .setLogin("kreker")
        .setPhone("+1235456")
        .setMail("anton@gmail.com");
    return userUpdateRequest;
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

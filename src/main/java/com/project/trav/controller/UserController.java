package com.project.trav.controller;

import com.project.trav.model.dto.UpdateUserPasswordRequest;
import com.project.trav.model.dto.UserUpdateRequest;
import com.project.trav.service.UserService;
import com.project.trav.model.dto.UserDto;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

  private final UserService userService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAuthority('admins')")
  public List<UserDto> getUsers() {
    return userService.getUsers();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAuthority('admins')")
  public UserDto getUser(@PathVariable Long id) {
    return userService.getUser(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto addUser(@Valid @RequestBody UserDto userDto) {
    return userService.addUser(userDto);
  }
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public UserDto updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest, @PathVariable Long id) {
   return userService.updateUser(userUpdateRequest, id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAuthority('admins')")
  public UserDto deleteUser(@PathVariable Long id) {
    return userService.deleteUser(id);
  }

  @PutMapping("/deactivate/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('admins')")
  public UserDto deactivateUser(@PathVariable Long id) {
    return userService.deactivateUser(id);
  }

  @PutMapping("/activate/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('admins')")
  public UserDto activateUser(@PathVariable Long id) {
    return userService.activateUser(id);
  }

  @PutMapping("/resetPasswordById/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAuthority('admins')")
  public void resetPasswordById(@Valid @RequestBody UpdateUserPasswordRequest userPasswordRequest,@PathVariable Long id) {
    userService.resetPasswordById(userPasswordRequest,id);
  }

  @PutMapping("/resetPassword")
  @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('admins','users')")
  public void resetPasswordById(@Valid @RequestBody UpdateUserPasswordRequest userPasswordRequest,
      HttpServletRequest httpServletRequest) {
    String username = httpServletRequest.getUserPrincipal().getName();
    userService.resetPassword(userPasswordRequest,username);
  }
}

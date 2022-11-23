package com.project.trav.controller;

import static com.project.trav.configuration.SecurityConfiguration.SECURITY_CONFIG_NAME;

import com.project.trav.model.dto.UpdateUserPasswordRequest;
import com.project.trav.model.dto.UserSaveRequest;
import com.project.trav.model.dto.UserUpdateRequest;
import com.project.trav.service.UserService;
import com.project.trav.model.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
  @SecurityRequirement(name = SECURITY_CONFIG_NAME)
  @Operation(summary = "Get all users", responses = {
      @ApiResponse(responseCode = "200", description = "Found users",
          content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))
          }),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAuthority('admins')")
  public List<UserDto> getUsers() {
    return userService.getUsers();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = SECURITY_CONFIG_NAME)
  @Operation(summary = "Get user by id", responses = {
      @ApiResponse(responseCode = "200", description = "Found user",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
          }),
      @ApiResponse(responseCode = "404", description = "User was not found by id", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAuthority('admins')")
  public UserDto getUser(@PathVariable Long id) {
    return userService.getUser(id);
  }
  @GetMapping("/myProfile")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get my profile", responses = {
      @ApiResponse(responseCode = "200", description = "your profile was found",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
          }),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @SecurityRequirement(name = SECURITY_CONFIG_NAME)
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public UserDto getMyProfile(HttpServletRequest request){
    return userService.getMyProfile(request.getUserPrincipal().getName());
  }
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create user", responses = {
      @ApiResponse(responseCode = "201", description = "User was created",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
          }),
      @ApiResponse(responseCode = "400", description = "Bad request check fields", content = @Content)
  })
  public UserDto addUser(@Valid @RequestBody UserSaveRequest userSaveRequest) {
    return userService.addUser(userSaveRequest);
  }
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = SECURITY_CONFIG_NAME)
  @Operation(summary = "Update user", responses = {
      @ApiResponse(responseCode = "200", description = "User was updated",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
          }),
      @ApiResponse(responseCode = "400", description = "Bad request check fields", content = @Content),
      @ApiResponse(responseCode = "404", description = "User was not found by id", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public UserDto updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest, @PathVariable Long id) {
   return userService.updateUser(userUpdateRequest, id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = SECURITY_CONFIG_NAME)
  @Operation(summary = "Delete user", responses = {
      @ApiResponse(responseCode = "200", description = "User was deleted",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
          }),
      @ApiResponse(responseCode = "404", description = "User was not found by id", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAuthority('admins')")
  public UserDto deleteUser(@PathVariable Long id) {
    return userService.deleteUser(id);
  }

  @PutMapping("/deactivate/{id}")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = SECURITY_CONFIG_NAME)
  @Operation(summary = "Deactivate user", responses = {
      @ApiResponse(responseCode = "200", description = "User was deactivated",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
          }),
      @ApiResponse(responseCode = "404", description = "User was not found by id", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAnyAuthority('admins')")
  public UserDto deactivateUser(@PathVariable Long id) {
    return userService.deactivateUser(id);
  }

  @PutMapping("/activate/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Activate user", responses = {
      @ApiResponse(responseCode = "200", description = "User was activated",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
          }),
      @ApiResponse(responseCode = "404", description = "User was not found by id", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @SecurityRequirement(name = SECURITY_CONFIG_NAME)
  @PreAuthorize("hasAnyAuthority('admins')")
  public UserDto activateUser(@PathVariable Long id) {
    return userService.activateUser(id);
  }

  @PutMapping("/resetPasswordById/{id}")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = SECURITY_CONFIG_NAME)
  @Operation(summary = "Reset user`s password by id", responses = {
      @ApiResponse(responseCode = "200", description = "User`s password was updated", content = @Content),
      @ApiResponse(responseCode = "400", description = "Bad request check fields", content = @Content),
      @ApiResponse(responseCode = "404", description = "User was not found by id", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAuthority('admins')")
  public void resetPasswordById(@Valid @RequestBody UpdateUserPasswordRequest userPasswordRequest,@PathVariable Long id) {
    userService.resetPasswordById(userPasswordRequest,id);
  }

  @PutMapping("/resetPassword")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = SECURITY_CONFIG_NAME)
  @Operation(summary = "Reset user`s password", responses = {
      @ApiResponse(responseCode = "200", description = "User`s password was updated", content = @Content),
      @ApiResponse(responseCode = "400", description = "Bad request check fields", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
    @PreAuthorize("hasAnyAuthority('admins','users')")
  public void resetPasswordById(@Valid @RequestBody UpdateUserPasswordRequest userPasswordRequest,
      HttpServletRequest httpServletRequest) {
    String username = httpServletRequest.getUserPrincipal().getName();
    userService.resetPassword(userPasswordRequest,username);
  }
}

package com.project.trav.ifrastructure.rest;

import com.project.trav.application.services.UserService;
import com.project.trav.ifrastructure.dto.UserDto;
import com.project.trav.ifrastructure.mapper.UserMapper;
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

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController{
    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('admins')")
    public List<UserDto> getUsers(){return userMapper.toUserDtos(userService.getUsers());}

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('admins')")
    public UserDto getUser(@PathVariable Long id){return userMapper.toUserDto(userService.getUser(id));}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("permitAll()")
    public void addUser(@RequestBody UserDto userDto){userService.addUser(userMapper.toUser(userDto));}

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('users','admins')")
    public void updateUser(@RequestBody UserDto userDto, @PathVariable Long id){
        userService.updateUser(userMapper.toUser(userDto),id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('admins')")
    public void deleteUser(@PathVariable Long id){userService.deleteUser(id);}

    @PutMapping("/deactivate/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('admins')")
    public void deactivateUser(@PathVariable Long id){userService.deactivateUser(id);}
}

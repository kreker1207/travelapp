package com.project.trav.service;

import com.project.trav.exeption.EntityAlreadyExists;
import com.project.trav.exeption.PasswordMatchException;
import com.project.trav.mapper.UserMapper;
import com.project.trav.model.dto.UpdateUserPasswordRequest;
import com.project.trav.model.dto.UserDto;
import com.project.trav.model.dto.UserUpdateRequest;
import com.project.trav.model.entity.Status;
import com.project.trav.model.entity.User;
import com.project.trav.repository.UserRepository;
import com.project.trav.exeption.EntityNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  private static final String NOT_FOUND_ERROR = "User was not found";
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public List<UserDto> getUsers() {
    return userMapper.toUserDtos(userRepository.findAll());
  }

  public UserDto getUser(Long id) {
    return userMapper.toUserDto(findByIdUser(id));
  }

  public UserDto addUser(UserDto userDto) {
    if (userRepository.existsUserByLoginOrMailOrPhone(userDto.getLogin(), userDto.getMail(),
        userDto.getPhone())) {
      throw new EntityAlreadyExists("User with this login/mail already exists");
    }
    userDto.setPassword(encryptPassword(userDto.getPassword()));
    userRepository.save(userMapper.toUser(userDto));
    return userDto;
  }

  public UserDto updateUser(UserUpdateRequest userUpdateRequest, Long id) {
    User oldUser = userMapper.toUser(getUser(id));
    validUpdate(userUpdateRequest, oldUser);
    userRepository.save(oldUser
        .setLogin(userUpdateRequest.getLogin())
        .setName(userUpdateRequest.getName())
        .setSurname(userUpdateRequest.getSurname())
        .setMail(userUpdateRequest.getMail())
        .setPhone(userUpdateRequest.getPhone())
        .setRole(userUpdateRequest.getRole())
        .setStatus(userUpdateRequest.getStatus())
    );
    return userMapper.toUserDto(oldUser);
  }

  public UserDto deleteUser(Long id) {
    User user = findByIdUser(id);
    userRepository.deleteById(id);
    return userMapper.toUserDto(user);
  }

  public UserDto deactivateUser(Long id) {
    User user = findByIdUser(id);
    user.setStatus(Status.BANNED);
    userRepository.save(user);
    return userMapper.toUserDto(user);
  }
  public UserDto activateUser(Long id) {
    User user = findByIdUser(id);
    user.setStatus(Status.ACTIVE);
    userRepository.save(user);
    return userMapper.toUserDto(user);
  }
  public void resetPassword(UpdateUserPasswordRequest userPasswordRequest,Long id){
    User user = userMapper.toUser(getUser(id));
    if (new BCryptPasswordEncoder().matches(userPasswordRequest.getOldPassword(), user.getPassword())){
      user.setPassword(encryptPassword(userPasswordRequest.getNewPassword()));
      userRepository.save(user);
    }
    else throw new PasswordMatchException("Check your password");
  }
  private User findByIdUser(Long id) {
    return userRepository.findById(id).orElseThrow(() -> {
      throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
    });

  }

  private void validUpdate(UserUpdateRequest user, User oldUser) {
    if ((!user.getLogin().equals(oldUser.getLogin()) || !user.getMail().equals(oldUser.getMail())
        || !user.getPhone().equals(oldUser.getPhone()))
        && userRepository.existsUserByLoginOrMailOrPhone(user.getLogin(), user.getMail(),
        user.getPhone())) {
      throw new EntityAlreadyExists("User with this login/mail already exist");
    }
  }
  private String encryptPassword(String password){
    return new BCryptPasswordEncoder().encode(password);
  }
}

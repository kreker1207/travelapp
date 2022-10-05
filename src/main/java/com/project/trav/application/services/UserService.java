package com.project.trav.application.services;

import com.project.trav.domain.entity.User;
import com.project.trav.domain.repository.UserRepository;
import com.project.trav.exeption.EntityNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String ERROR_TEMPLATE = "User was not found";
    private final UserRepository userRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow(()->{
            throw new EntityNotFoundByIdException(ERROR_TEMPLATE);
        });
    }
    public void addUser(User user){
        userRepository.save(user);
    }
    public void updateUser(User user, Long id){
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundByIdException(ERROR_TEMPLATE);
        }
        User oldUser = getUser(id);
        userRepository.save(User.builder()
                .id(id)
                .name(Objects.isNull(user.getName())?oldUser.getName():user.getName())
                .surname(Objects.isNull(user.getSurname())?oldUser.getSurname():user.getSurname())
                .mail(Objects.isNull(user.getMail())?oldUser.getMail():user.getMail())
                .phone(Objects.isNull(user.getPhone())?oldUser.getPhone():user.getPhone())
                .login(Objects.isNull(user.getLogin())?oldUser.getLogin():user.getLogin())
                .password(Objects.isNull(user.getPassword())?oldUser.getPassword():user.getPassword())
                .build()
        );
    }
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundByIdException(ERROR_TEMPLATE);
        }
        userRepository.deleteById(id);
    }
}

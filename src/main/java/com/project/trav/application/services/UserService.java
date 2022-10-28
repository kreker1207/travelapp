package com.project.trav.application.services;

import com.project.trav.domain.entity.Status;
import com.project.trav.domain.entity.User;
import com.project.trav.domain.repository.UserRepository;
import com.project.trav.exeption.EntityAlreadyExists;
import com.project.trav.exeption.EntityNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String NOT_FOUND_ERROR = "User was not found";
    private final UserRepository userRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow(()->{
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        });
    }
    public void addUser(User user){
        if(Objects.isNull(userRepository.findByLogin(user.getLogin())) ||
                Objects.isNull(userRepository.findByMail(user.getMail()))) {
            userRepository.save(user);
        }
        else throw new EntityAlreadyExists("User already exists");
    }
    public void updateUser(User user, Long id){
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        }
        if(Objects.isNull(userRepository.findByLogin(user.getLogin())) ||
                Objects.isNull(userRepository.findByMail(user.getMail()))) {
            userRepository.save(user);
        }
        else throw new EntityAlreadyExists("User already exists");
    }
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        }
        userRepository.deleteById(id);
    }
    public void deactivateUser(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        }
        User user = userOptional.get();
        user.setStatus(Status.BANNED);
        updateUser(user,id);
    }
}

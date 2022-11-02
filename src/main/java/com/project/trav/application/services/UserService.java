package com.project.trav.application.services;

import com.project.trav.domain.entity.Status;
import com.project.trav.domain.entity.User;
import com.project.trav.domain.repository.UserRepository;
import com.project.trav.exeption.EntityAlreadyExists;
import com.project.trav.exeption.EntityNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if(userRepository.existsUserByLoginOrMail(user.getLogin(), user.getMail())) {
            throw new EntityAlreadyExists("User already exists");
        }
        userRepository.save(user);
    }
    public void updateUser(User user, Long id){
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        }
        User oldUser = getUser(id);
        if(user.getLogin()!=null && !user.getLogin().equals(oldUser.getLogin()) && userRepository.existsUserByLogin(user.getLogin())){
            throw new EntityAlreadyExists("User with this login already exist");
        }
        if(user.getMail()!=null && !user.getMail().equals(oldUser.getMail()) && userRepository.existsUserByMail(user.getMail())){
            throw new EntityAlreadyExists("User with this mail already exist");
        }
        userRepository.save(new User()
                .setId(id)
                .setLogin(user.getLogin() == null ? oldUser.getLogin() : user.getLogin())
                .setName(user.getSurname() == null ? oldUser.getSurname() : user.getSurname())
                .setSurname(user.getSurname() == null ? oldUser.getSurname() : user.getSurname())
                .setPassword(user.getPassword()==null?oldUser.getPassword():user.getPassword())
                .setMail(user.getMail() == null ? oldUser.getMail() : user.getMail())
                .setPhone(user.getPhone() == null ? oldUser.getPhone() : user.getPhone())
                .setRole(user.getRole()==null?oldUser.getRole():user.getRole())
                .setStatus(user.getStatus()==null?oldUser.getStatus():user.getStatus())
                );
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

package com.project.trav.repository;

import com.project.trav.model.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByLogin(String login);
  List<User> findUserByLoginOrMailOrPhone(String login, String mail,String phone);
}

package com.project.trav.repository;

import com.project.trav.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByLogin(String login);
  Boolean existsUserByLoginOrMail(String login, String mail);
  Boolean existsUserByLogin(String login);
  Boolean existsUserByMail(String mail);
}
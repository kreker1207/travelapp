package com.project.trav.repository;

import com.project.trav.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserH2Repository extends JpaRepository<User,Long> {

}

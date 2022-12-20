package com.project.trav.repository;

import com.project.trav.model.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceH2Repository extends JpaRepository<Race,Long>{

}

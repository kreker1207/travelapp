package com.project.trav.domain.repository;

import com.project.trav.domain.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<Race,Long> {
}

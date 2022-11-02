package com.project.trav.repository;

import com.project.trav.model.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {

  List<Race> DepartureTimeAndArrivalTime(String departureTime, String arrivalTime);

}

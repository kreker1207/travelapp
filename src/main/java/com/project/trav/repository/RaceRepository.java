package com.project.trav.repository;

import com.project.trav.model.entity.Race;
import java.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {

  List<Race> DepartureTimeAndArrivalTime(LocalTime departureTime, LocalTime arrivalTime);
  Boolean existsRaceByRaceNumber(String raceNumber);

}

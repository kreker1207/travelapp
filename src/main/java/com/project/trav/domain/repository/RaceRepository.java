package com.project.trav.domain.repository;

import com.project.trav.domain.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceRepository extends JpaRepository<Race,Long> {
    List<Race> findByDepartureCityAndArrivalCityOrDepartureTimeAndArrivalTime(String departureCity,String arrivalCit,String departureTime, String arrivalTime);

}

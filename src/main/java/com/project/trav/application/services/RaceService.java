package com.project.trav.application.services;

import com.project.trav.domain.entity.Race;
import com.project.trav.domain.repository.RaceRepository;
import com.project.trav.exeption.EntityAlreadyExists;
import com.project.trav.exeption.EntityNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RaceService {
    private static final String NOT_FOUND_ERROR = "Race was not found by id";
    private final RaceRepository raceRepository;

    public List<Race> getRaces(){return raceRepository.findAll();}
    public Race getRace(Long id) {
        return raceRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        });
    }
    public void addRace(Race race){
        if (raceRepository.existsRaceByRaceNumber(race.getRaceNumber())) {
        throw new EntityAlreadyExists("Race with this Number already exists");
        }
        raceRepository.save(race);
    }
    public void deleteRace(Long id){
        if(!raceRepository.existsById(id)){
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        }
        raceRepository.deleteById(id);
    }
    public void updateRace(Race race,Long id){
        if(!raceRepository.existsById(id)){
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        }
        if (raceRepository.existsRaceByRaceNumber(race.getRaceNumber())) {
            throw new EntityAlreadyExists("Race with this Number already exists");
        }
        raceRepository.save(race);
    }
    public List<Race> searchByParams(String departureCityParam, String arrivalCityParam, LocalTime departureTimeParam, LocalTime arrivalTimeParam){
        return raceRepository.findByDepartureCityAndArrivalCityOrDepartureTimeAndArrivalTime(departureCityParam,arrivalCityParam,departureTimeParam,arrivalTimeParam);
    }
}

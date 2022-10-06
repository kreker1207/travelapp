package com.project.trav.application.services;

import com.project.trav.domain.entity.Race;
import com.project.trav.domain.repository.RaceRepository;
import com.project.trav.exeption.EntityNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    public void addRace(Race race){ raceRepository.save(race);}
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
        Race oldRace = getRace(id);
        raceRepository.save(Race.builder()
                .id(id)
                .departureTime(Objects.isNull(race.getDepartureTime())?oldRace.getDepartureTime():race.getDepartureTime())
                .arrivalTime(Objects.isNull(race.getArrivalTime())?oldRace.getArrivalTime(): race.getArrivalTime())
                .departureCity(Objects.isNull(race.getDepartureCity())?oldRace.getDepartureCity(): race.getDepartureCity())
                .arrivalCity(Objects.isNull(race.getArrivalCity())?oldRace.getArrivalCity(): race.getArrivalCity())
                .travelTime(Objects.isNull(race.getTravelTime())?oldRace.getTravelTime(): race.getTravelTime())
                .airline(Objects.isNull(race.getAirline())?oldRace.getAirline():race.getAirline())
                .build()
        );
    }
}

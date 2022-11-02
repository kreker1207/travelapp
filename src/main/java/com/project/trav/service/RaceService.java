package com.project.trav.service;

import com.project.trav.exeption.EntityAlreadyExists;
import com.project.trav.mapper.RaceMapper;
import com.project.trav.model.dto.RaceDto;
import com.project.trav.model.entity.Race;
import com.project.trav.repository.RaceRepository;
import com.project.trav.exeption.EntityNotFoundByIdException;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RaceService {

  private static final String NOT_FOUND_ERROR = "Race was not found by id";
  private final RaceRepository raceRepository;
  private final RaceMapper raceMapper;

  public List<RaceDto> getRaces() {
    return raceMapper.toRaceDtos(raceRepository.findAll());
  }

  public RaceDto getRace(Long id) {
    return raceMapper.toRaceDto(findByIdRace(id));
  }

  public void addRace(RaceDto raceDto) {
    existByNumber(raceDto.getRaceNumber());
    raceRepository.save(raceMapper.toRace(raceDto));
  }

  public void deleteRace(Long id) {
    existByIdRace(id);
    raceRepository.deleteById(id);
  }

  public void updateRace(RaceDto raceDto, Long id) {
    existByIdRace(id);
    Race race = raceMapper.toRace(raceDto);
    Race oldRace = raceMapper.toRace(getRace(id));
    existByNumber(race,oldRace);
    raceRepository.save(new Race()
        .setId(id)
        .setDepartureTime(
            race.getDepartureTime() == null ? oldRace.getDepartureTime() : race.getDepartureTime())
        .setArrivalTime(
            race.getArrivalTime() == null ? oldRace.getArrivalTime() : race.getArrivalTime())
        .setTravelTime(
            race.getTravelTime() == null ? oldRace.getTravelTime() : race.getTravelTime())
        .setAirline(race.getAirline() == null ? oldRace.getAirline() : race.getAirline())
        .setRaceNumber(
            race.getRaceNumber() == null ? oldRace.getRaceNumber() : race.getRaceNumber())
        .setDepartureCityId(race.getDepartureCityId() == null ? oldRace.getDepartureCityId()
            : race.getDepartureCityId())
        .setArrivalCityId(
            race.getArrivalCityId() == null ? oldRace.getArrivalCityId() : race.getArrivalCityId())
    );
  }

  public List<RaceDto> searchByParams(LocalTime departureTimeParam, LocalTime arrivalTimeParam) {
    return raceMapper.toRaceDtos(
        raceRepository.DepartureTimeAndArrivalTime(departureTimeParam, arrivalTimeParam));
  }

  private Race findByIdRace(Long id) {
    return raceRepository.findById(id).orElseThrow(() -> {
      throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
    });

  }

  private void existByIdRace(Long id) {
    if (!raceRepository.existsById(id)) {
      throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
    }
  }
  private void existByNumber(String number){
    if (!number.isEmpty() && raceRepository.existsRaceByRaceNumber(number)) {
      throw new EntityAlreadyExists("Race with this Number already exists");
    }
  }
  private void existByNumber(Race race, Race oldRace) {
    if (race.getRaceNumber() != null && !race.getRaceNumber().equals(oldRace.getRaceNumber())
        && raceRepository.existsRaceByRaceNumber(race.getRaceNumber())) {
      throw new EntityAlreadyExists("Race with this Number already exists");
    }
  }
}

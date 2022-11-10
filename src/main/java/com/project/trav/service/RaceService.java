package com.project.trav.service;

import com.project.trav.exeption.EntityAlreadyExists;
import com.project.trav.mapper.RaceMapper;
import com.project.trav.model.dto.RaceDto;
import com.project.trav.model.dto.RaceUpdateRequest;
import com.project.trav.model.entity.City;
import com.project.trav.model.entity.Race;
import com.project.trav.repository.RaceRepository;
import com.project.trav.exeption.EntityNotFoundByIdException;
import java.time.Duration;
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
    verifyRaceExists(raceDto.getId(), raceDto.getRaceNumber());
    Duration d = Duration.between(raceDto.getDepartureDateTime(), raceDto.getArrivalDateTime());
    raceDto.setTravelTimeDuration(d.abs());
    raceRepository.save(raceMapper.toRace(raceDto));
  }

  public void deleteRace(Long id) {
    findByIdRace(id);
    raceRepository.deleteById(id);
  }

  public void updateRace(RaceUpdateRequest raceUpdateRequest, Long id) {
    findByIdRace(id);
    Race oldRace = raceMapper.toRace(getRace(id));
    verifyRaceExists(raceUpdateRequest.getId(), raceUpdateRequest.getRaceNumber());
    raceRepository.save(oldRace
        .setDepartureDateTime(
            raceUpdateRequest.getDepartureDateTime())
        .setArrivalDateTime(
            raceUpdateRequest.getArrivalDateTime())
        .setTravelTimeDuration(
            Duration.between(raceUpdateRequest.getArrivalDateTime(),
                raceUpdateRequest.getDepartureDateTime()).abs())
        .setAirline(raceUpdateRequest.getAirline())
        .setRaceNumber(
            raceUpdateRequest.getRaceNumber())
        .setDepartureCity(new City().setId(raceUpdateRequest.getDepartureCityId())
            .setName(raceUpdateRequest.getDepartureCityName())
            .setCountry(raceUpdateRequest.getDepartureCityCountry())
            .setPopulation(raceUpdateRequest.getDepartureCityPopulation())
            .setInformation(raceUpdateRequest.getDepartureCityInformation()))
        .setArrivalCity(new City().setId(
                raceUpdateRequest.getArrivalCityId())
            .setName(raceUpdateRequest.getArrivalCityName())
            .setCountry(raceUpdateRequest.getArrivalCityCountry())
            .setPopulation(raceUpdateRequest.getArrivalCityPopulation())
            .setInformation(raceUpdateRequest.getArrivalCityInformation())));
  }

  private Race findByIdRace(Long id) {
    return raceRepository.findById(id).orElseThrow(() -> {
      throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
    });

  }

  private void verifyRaceExists(Long raceId, String raceNumber) {
    var raceOptional = raceRepository.findByRaceNumber(raceNumber);
    if (raceId != null) {
      raceOptional = raceOptional.filter(race -> !race.getId().equals(raceId));
    }
    raceOptional.ifPresent(race -> {
      throw new EntityAlreadyExists("Race with this Number already exists");
    });
  }

}

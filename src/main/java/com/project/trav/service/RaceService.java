package com.project.trav.service;

import com.project.trav.exeption.EntityAlreadyExists;
import com.project.trav.mapper.RaceMapper;
import com.project.trav.model.dto.RaceDto;
import com.project.trav.model.dto.RaceSaveRequest;
import com.project.trav.model.dto.RaceUpdateRequest;
import com.project.trav.model.dto.SendLogsKafka;
import com.project.trav.model.entity.City;
import com.project.trav.model.entity.Race;
import com.project.trav.repository.CityRepository;
import com.project.trav.repository.RaceRepository;
import com.project.trav.exeption.EntityNotFoundByIdException;
import com.querydsl.core.types.Predicate;
import java.time.Duration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RaceService {

  private static final String NOT_FOUND_ERROR = "Race was not found by id";
  private final RaceRepository raceRepository;
  private final KafkaTemplate<String, SendLogsKafka> kafkaKafkaTemplate;
  private final CityRepository cityRepository;
  private final RaceMapper raceMapper;

  public List<RaceDto> getRaces() {
    return raceMapper.toRaceDtos(raceRepository.findAll());
  }

  public RaceDto getRace(Long id) {
    return raceMapper.toRaceDto(findByIdRace(id));
  }

  public RaceDto addRace(RaceSaveRequest raceSaveRequest) {
    verifyRaceExists(null, raceSaveRequest.getRaceNumber());
    Duration d = Duration.between(raceSaveRequest.getDepartureDateTime(), raceSaveRequest.getArrivalDateTime());
    Race race = new Race().setTravelTimeDuration(d.abs()).setDepartureDateTime(raceSaveRequest.getDepartureDateTime()).setArrivalDateTime(
            raceSaveRequest.getArrivalDateTime())
        .setRaceNumber(raceSaveRequest.getRaceNumber()).setAirline(raceSaveRequest.getAirline()).setTravelTimeDuration(d)
        .setArrivalCity(getValidCity(raceSaveRequest.getArrivalCityId()))
        .setDepartureCity(getValidCity(raceSaveRequest.getDepartureCityId()));
    raceRepository.save(race);
    return raceMapper.toRaceDto(race);
  }

  public RaceDto deleteRace(Long id) {
    Race race = findByIdRace(id);
    raceRepository.deleteById(id);
    return raceMapper.toRaceDto(race);
  }

  public RaceDto updateRace(RaceUpdateRequest raceUpdateRequest, Long id) {
    findByIdRace(id);
    Race oldRace = raceMapper.toRace(getRace(id));
    verifyRaceExists(id, raceUpdateRequest.getRaceNumber());
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
        .setDepartureCity(getValidCity(raceUpdateRequest.getDepartureCityId()))
        .setArrivalCity(getValidCity(raceUpdateRequest.getArrivalCityId())));
    return raceMapper.toRaceDto(oldRace);
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
  private City getValidCity(Long cityId){
    return cityRepository.findById(cityId).orElseThrow(()->{throw new EntityNotFoundByIdException("City was not found by id");});
  }

  public Page<RaceDto> searchRaces(Predicate predicate, Pageable pageable,String login) {
    kafkaKafkaTemplate.send("logsTopic",new SendLogsKafka()
        .setLogin(login)
        .setSearchParams(predicate.toString()));
    return raceRepository.findAll(predicate,pageable).map(raceMapper::toRaceDto);
  }
}

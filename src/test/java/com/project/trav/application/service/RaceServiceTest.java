package com.project.trav.application.service;

import com.project.trav.mapper.RaceMapper;
import com.project.trav.model.dto.CityDto;
import com.project.trav.model.dto.RaceDto;
import com.project.trav.model.dto.RaceUpdateRequest;
import com.project.trav.service.RaceService;
import com.project.trav.model.entity.City;
import com.project.trav.model.entity.Race;
import com.project.trav.repository.RaceRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class RaceServiceTest {

  @Mock
  private RaceMapper raceMapper;
  @Mock
  private RaceRepository raceRepository;
  @InjectMocks
  private RaceService raceService;
  City city = new City().setId(1L).setName("Kiev").setCountry("Ukraine")
      .setPopulation("2.7 million").setInformation("Capital");
  CityDto cityDto = new CityDto().setId(1L).setName("Kiev").setCountry("Ukraine")
      .setPopulation("2.7 million").setInformation("Capital");

  @Test
  void getRaces() {
    var raceList = Arrays.asList(
        new RaceDto().setId(1L).setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
            .setArrivalDateTime(LocalDateTime.parse("2022-11-02T15:00"))
            .setTravelTime(LocalTime.parse("03:00"))
            .setAirline("Mau").setRaceNumber("Wr23-ww").setDepartureCityDto(cityDto)
            .setArrivalCityDto(cityDto),
        new RaceDto().setId(1L).setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
            .setArrivalDateTime(LocalDateTime.parse("2022-11-02T15:00"))
            .setTravelTime(LocalTime.parse("03:00"))
            .setAirline("Mau").setRaceNumber("Wr23-ww").setDepartureCityDto(cityDto)
            .setArrivalCityDto(cityDto)
    );
    Mockito.when(raceMapper.toRaceDtos(Mockito.anyList())).thenReturn(raceList);
    var expectedList = raceService.getRaces();
    assertThat(expectedList).isEqualTo(raceList);
  }

  @Test
  void getRace_success() {
    var sourceRace = new Race().setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T15:00"))
        .setTravelTime(LocalTime.parse("03:00")).setAirline("Mau").setRaceNumber("Wr23-ww")
        .setDepartureCity(city)
        .setArrivalCity(city);
    Mockito.when(raceRepository.findById(1L)).thenReturn(Optional.of(sourceRace));
    var expectedRace = raceService.getRace(1L);
    assertThat(raceMapper.toRaceDto(sourceRace)).isEqualTo(expectedRace);
  }

  @Test
  void getRace_failure() {
    Mockito.when(raceRepository.findById(1L)).thenReturn(Optional.empty());
    Assertions.assertThrows(EntityNotFoundException.class, () -> raceService.getRace(1L));
  }

  @Test
  void deleteRace_success() {
    Mockito.when(raceRepository.existsById(1L)).thenReturn(true);
    raceService.deleteRace(1L);
    Mockito.verify(raceRepository).deleteById(1L);
  }

  @Test
  void deleteRace_failure() {
    Mockito.when(raceRepository.existsById(1L)).thenReturn(false);
    String expectedMessage = "Race was not found by id";
    String actualMessage = Assertions.assertThrows(EntityNotFoundException.class, () ->
        raceService.deleteRace(1L)).getMessage();
    assertThat(expectedMessage).isEqualTo(actualMessage);
  }

  @Test
  void addRace() {
    var race = new RaceDto().setId(1L).setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T15:00"))
        .setTravelTime(LocalTime.parse("03:00")).setAirline("Mau").setRaceNumber("Wr23-ww")
        .setDepartureCityDto(cityDto).setArrivalCityDto(cityDto);
    raceService.addRace(race);
    Mockito.verify(raceRepository).save(raceMapper.toRace(race));
  }

  @Test
  void updateRace_success() {
    var race = new Race().setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T15:00"))
        .setTravelTime(LocalTime.parse("03:00")).setAirline("Mau").setRaceNumber("Wr23-ww")
        .setDepartureCity(city)
        .setArrivalCity(city);
    var sourceRace = new RaceUpdateRequest()
        .setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T15:00"))
        .setTravelTime(LocalTime.parse("03:00")).setAirline("Mau").setRaceNumber("Wr23-ww")
        .setDepartureCityId(city.getId()).setDepartureCityName(city.getName()).setDepartureCityCountry(city.getCountry())
        .setDepartureCityPopulation(city.getPopulation()).setDepartureCityInformation(city.getInformation())
        .setArrivalCityId(city.getId()).setArrivalCityName(city.getName()).setArrivalCityCountry(city.getCountry())
        .setArrivalCityPopulation(city.getPopulation()).setArrivalCityInformation(city.getInformation());
    Mockito.when(raceRepository.existsById(1L)).thenReturn(true);
    Mockito.when(raceRepository.findById(1L)).thenReturn(Optional.of(race));
    Mockito.when(raceMapper.toRace(raceService.getRace(1L))).thenReturn(race);

    raceService.updateRace(sourceRace, 1L);
    Mockito.verify(raceRepository).save(race);
  }

  @Test
  void updateRace_failure() {
    var race = new RaceUpdateRequest()
        .setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T15:00"))
        .setTravelTime(LocalTime.parse("03:00")).setAirline("Mau").setRaceNumber("Wr23-ww")
        .setDepartureCityId(city.getId()).setDepartureCityName(city.getName()).setDepartureCityCountry(city.getCountry())
        .setDepartureCityPopulation(city.getPopulation()).setDepartureCityInformation(city.getInformation())
        .setArrivalCityId(city.getId()).setArrivalCityName(city.getName()).setArrivalCityCountry(city.getCountry())
        .setArrivalCityPopulation(city.getPopulation()).setArrivalCityInformation(city.getInformation());

    Mockito.when(raceRepository.existsById(1L)).thenReturn(false);
    String expectedMessage = "Race was not found by id";
    String actualMessage = Assertions.assertThrows(EntityNotFoundException.class, () ->
        raceService.updateRace(race, 1L)).getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }
}

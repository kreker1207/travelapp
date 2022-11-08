package com.project.trav.infrastructure.mapper;

import com.project.trav.model.dto.CityDto;
import com.project.trav.model.entity.City;
import com.project.trav.model.entity.Race;
import com.project.trav.model.dto.RaceDto;
import com.project.trav.mapper.CityMapperImpl;
import com.project.trav.mapper.RaceMapper;
import com.project.trav.mapper.RaceMapperImpl;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RaceMapperImpl.class, CityMapperImpl.class})
public class RaceMapperTest {

  @Autowired
  private RaceMapper mapper;
  City city = new City().setName("Kiev").setCountry("Ukraine").setPopulation("3.3 million")
      .setInformation("capital");
  CityDto cityDto = new CityDto().setName("Kiev").setCountry("Ukraine").setPopulation("3.3 million")
      .setInformation("capital");

  @Test
  void toRace() {
    var sourceRaceDto = new RaceDto()
        .setId(1L)
        .setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T15:00"))
        .setTravelTimeDuration(Duration.ZERO)
        .setAirline("Mau")
        .setRaceNumber("Wz-air-222")
        .setDepartureCityDto(cityDto)
        .setArrivalCityDto(cityDto);
    var resultRace = mapper.toRace(sourceRaceDto);
    var raceExpected = new Race()
        .setId(1L)
        .setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T15:00"))
        .setTravelTimeDuration(Duration.ZERO)
        .setAirline("Mau")
        .setRaceNumber("Wz-air-222")
        .setDepartureCity(city)
        .setArrivalCity(city);
    assertThat(resultRace).isEqualTo(raceExpected);
  }

  @Test
  void toRaceDtos() {
    var sourceRaceList = Arrays.asList(new Race()
        .setId(1L)
        .setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T15:00"))
        .setTravelTimeDuration(Duration.ZERO)
        .setAirline("Mau")
        .setRaceNumber("Wz-air-222")
        .setDepartureCity(city)
        .setArrivalCity(city), new Race()
        .setId(1L)
        .setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T15:00"))
        .setTravelTimeDuration(Duration.ZERO)
        .setAirline("Mau")
        .setRaceNumber("Wz-air-222")
        .setDepartureCity(city)
        .setArrivalCity(city));
    var resultRacesDto = mapper.toRaceDtos(sourceRaceList);
    var expectedRaceList = Arrays.asList(new RaceDto()
        .setId(1L)
        .setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T15:00"))
        .setTravelTimeDuration(Duration.ZERO)
        .setAirline("Mau")
        .setRaceNumber("Wz-air-222")
        .setDepartureCityDto(cityDto)
        .setArrivalCityDto(cityDto), new RaceDto()
        .setId(1L)
        .setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T15:00"))
        .setTravelTimeDuration(Duration.ZERO)
        .setAirline("Mau")
        .setRaceNumber("Wz-air-222")
        .setDepartureCityDto(cityDto)
        .setArrivalCityDto(cityDto));
    assertThat(resultRacesDto).isEqualTo(expectedRaceList);
  }

  @Test
  void toRaceDto() {
    var sourceRace = new Race()
        .setId(1L)
        .setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T15:00"))
        .setTravelTimeDuration(Duration.ZERO)
        .setAirline("Mau")
        .setRaceNumber("Wz-air-222")
        .setDepartureCity(city)
        .setArrivalCity(city);
    var resultRaceDto = mapper.toRaceDto(sourceRace);
    var raceDtoExpected = new RaceDto()
        .setId(1L)
        .setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T15:00"))
        .setTravelTimeDuration(Duration.ZERO)
        .setAirline("Mau")
        .setRaceNumber("Wz-air-222")
        .setDepartureCityDto(cityDto)
        .setArrivalCityDto(cityDto);
    assertThat(resultRaceDto).isEqualTo(raceDtoExpected);
  }

}

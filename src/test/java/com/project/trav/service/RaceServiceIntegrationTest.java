package com.project.trav.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.trav.TravelApplication;
import com.project.trav.exeption.EntityAlreadyExists;
import com.project.trav.exeption.EntityNotFoundByIdException;
import com.project.trav.model.dto.RaceDto.Fields;
import com.project.trav.model.dto.RaceDto;
import com.project.trav.model.dto.RaceSaveRequest;
import com.project.trav.model.dto.RaceUpdateRequest;

import com.project.trav.repository.RaceH2Repository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = TravelApplication.class)
@ExtendWith(SpringExtension.class)
@Transactional
public class RaceServiceIntegrationTest {

  @Autowired
  private RaceService raceService;
  @Autowired
  private RaceH2Repository raceH2Repository;

  @AfterEach
  public void clearDataBase() {
    raceH2Repository.deleteAll();
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  void createRace_success() {
    assertEquals(0, raceH2Repository.findAll().size());
    var expectedRaceDto = new RaceDto().setRaceNumber("fds1-233").setAirline("Mau")
        .setDepartureDateTime(LocalDateTime.parse("2022-11-02T12:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T13:00:00"));
    var resultRaceDto = raceService.addRace(createRaceSaveRequest());
    assertThat(resultRaceDto).usingRecursiveComparison()
        .ignoringFields(Fields.id, Fields.arrivalCityDto, Fields.departureCityDto,
            Fields.ticketDtoList, Fields.travelTimeDuration)
        .isEqualTo(expectedRaceDto);
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  void createRaceUniqueField_fail() {
    assertEquals("fds1-233", raceH2Repository.findById(1L).get().getRaceNumber());
    assertThrows(EntityAlreadyExists.class, () -> {
      raceService.addRace(createRaceSaveRequest());
    }, "Race with this Number already exists");

  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (3,'Berlin','Berlin','4.2m','Capital')")
  void createRaceCityNotFound_fail() {
    assertFalse(raceH2Repository.findById(3L).isPresent());
    assertThrows(EntityNotFoundByIdException.class, () -> {
      raceService.addRace(createRaceSaveRequest());
    }, "City was not found by id");

  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  void getRace_success() {
    var raceDto = raceService.getRace(1L);
    assertEquals(1, raceH2Repository.findAll().size());
    assertEquals("fds1-233", raceDto.getRaceNumber());
  }

  @Test
  void getRaceNotFound_fail() {
    assertEquals(0, raceH2Repository.findAll().size());
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      raceService.getRace(1L);
    }, "Race was not found by id");
    assertEquals("Race was not found by id", thrown.getMessage());
  }

  @Test
  void deleteRaceNotFound_fail() {
    assertEquals(0, raceH2Repository.findAll().size());
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      raceService.deleteRace(1L);
    }, "Race was not found by id");
    assertEquals("Race was not found by id", thrown.getMessage());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (2,'2022-11-02T15:00:00','2022-11-02T16:00:00','3600000','Reynar','Dsa-321-wwwa',2,1)")
  void getRaces_success() {
    var foundRace = raceService.getRaces();
    assertEquals(2, raceH2Repository.findAll().size());
    assertEquals(2, foundRace.size());
    assertEquals("Reynar", foundRace.get(1).getAirline());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (2,'2022-11-02T15:00:00','2022-11-02T16:00:00','3600000','Reynar','Dsa-321-wwwa',2,1)")
  void deleteRace() {
    assertEquals(2, raceH2Repository.findAll().size());
    raceService.deleteRace(1L);
    assertEquals(1, raceH2Repository.findAll().size());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','456-233',1,2)")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (2,'2022-11-02T15:00:00','2022-11-02T16:00:00','3600000','Reynar','Dsa-321-wwwa',2,1)")
  void updateRace_success() {
    assertEquals("Reynar", raceService.getRace(2L).getAirline());
    var expectedRaceDto = new RaceDto().setRaceNumber("fds1-233").setAirline("Mau")
        .setDepartureDateTime(LocalDateTime.parse("2022-11-02T15:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T16:00:00"));
    var resultRaceDto = raceService.updateRace(createRaceUpdateRequest(), 2L);
    assertThat(resultRaceDto).usingRecursiveComparison()
        .ignoringFields(Fields.id, Fields.arrivalCityDto, Fields.departureCityDto,
            Fields.ticketDtoList, Fields.travelTimeDuration)
        .isEqualTo(expectedRaceDto);
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','VDSwqe21',1,2)")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (2,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  void updateUniqueField_fail() {
    EntityAlreadyExists thrown = assertThrows(EntityAlreadyExists.class, () -> {
      raceService.updateRace(createRaceUpdateRequest(), 1L);
    }, "Race with this Number already exists");
    assertEquals("Race with this Number already exists", thrown.getMessage());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (2,'2022-11-02T15:00:00','2022-11-02T16:00:00','3600000','Reynar','Dsa-321-wwwa',2,1)")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (3,'2022-11-02T18:00:00','2022-11-02T19:00:00','3600000','Mau','eww2-gfa',2,1)")
  void searchRace_success() {
    assertEquals(3, raceH2Repository.findAll().size());
  }

  private RaceSaveRequest createRaceSaveRequest() {
    return new RaceSaveRequest().setRaceNumber("fds1-233").setAirline("Mau").setDepartureDateTime(
            LocalDateTime.parse("2022-11-02T12:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T13:00:00"))
        .setDepartureCityId(1L).setArrivalCityId(2L);
  }

  private RaceUpdateRequest createRaceUpdateRequest() {
    return new RaceUpdateRequest().setRaceNumber("fds1-233").setAirline("Mau")
        .setDepartureDateTime(LocalDateTime.parse("2022-11-02T15:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T16:00:00")).setArrivalCityId(1L)
        .setDepartureCityId(2L);
  }
}

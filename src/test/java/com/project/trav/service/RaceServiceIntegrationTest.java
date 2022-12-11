package com.project.trav.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.project.trav.TravelApplication;
import com.project.trav.model.dto.RaceDto;
import com.project.trav.model.dto.RaceSaveRequest;
import com.project.trav.model.dto.RaceUpdateRequest;

import com.project.trav.repository.RaceH2Repository;
import java.time.LocalDateTime;
import java.util.List;
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
  void createRace() {
    assertEquals(0, raceH2Repository.findAll().size());
    raceService.addRace(
        new RaceSaveRequest().setRaceNumber("ewqr-231").setAirline("Mau").setDepartureDateTime(
                LocalDateTime.parse("2022-11-02T12:00:00"))
            .setArrivalDateTime(LocalDateTime.parse("2022-11-02T13:00:00"))
            .setDepartureCityId(1L).setArrivalCityId(2L));
    List<RaceDto> foundRace = raceService.getRaces();
    assertEquals(1, raceH2Repository.findAll().size());
    assertEquals("Mau", foundRace.get(0).getAirline());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  void getRace() {
    RaceDto raceDto = raceService.getRace(1L);
    assertEquals(1, raceH2Repository.findAll().size());
    assertEquals("fds1-233", raceDto.getRaceNumber());
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
  void getRaces() {
    List<RaceDto> foundRace = raceService.getRaces();
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
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (2,'2022-11-02T15:00:00','2022-11-02T16:00:00','3600000','Reynar','Dsa-321-wwwa',2,1)")
  void updateRace() {
    assertEquals("Reynar", raceService.getRace(2L).getAirline());
    raceService.updateRace(new RaceUpdateRequest().setAirline("MajongAir")
        .setDepartureDateTime(LocalDateTime.parse("2022-11-02T15:00:00"))
        .setArrivalDateTime(LocalDateTime.parse("2022-11-02T16:00:00"))
        .setRaceNumber("Dsa-321-wwwa").setDepartureCityId(2L).setArrivalCityId(1L), 2L);
    assertEquals("MajongAir", raceService.getRace(2L).getAirline());
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
  void searchRace() {
    assertEquals(3, raceH2Repository.findAll().size());
  }
}

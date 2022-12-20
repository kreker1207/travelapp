package com.project.trav.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.project.trav.TravelApplication;
import com.project.trav.exeption.EntityAlreadyExists;
import com.project.trav.exeption.EntityNotFoundByIdException;
import com.project.trav.exeption.TicketReservingException;
import com.project.trav.model.dto.TicketDto;
import com.project.trav.model.dto.TicketSaveRequest;
import com.project.trav.model.dto.TicketUpdateRequest;
import com.project.trav.model.dto.TicketDto.Fields;
import com.project.trav.model.entity.TicketStatus;
import com.project.trav.repository.TicketH2Repository;
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
public class TicketServiceIntegrationTest {

  @Autowired
  private TicketService ticketService;
  @Autowired
  private TicketH2Repository ticketH2Repository;

  @AfterEach
  public void clearDataBase() {
    ticketH2Repository.deleteAll();
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  void createTicket_success() {
    var expectedTicket = new TicketDto().setTicketStatus(TicketStatus.AVAILABLE).setCost("200$")
        .setPlace("A23").setPlaceClass("econom");
    var resultTicket = ticketService.addTicket(createTicketSaveRequest());
    assertEquals(1, ticketH2Repository.findAll().size());
    assertThat(resultTicket).usingRecursiveComparison().ignoringFields(Fields.id, Fields.racesDto)
        .isEqualTo(expectedTicket);
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (2,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  void createTicketRaceNotFound_fail() {
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      ticketService.addTicket(createTicketSaveRequest());
    }, "Race was not found");
    assertEquals("Race was not found", thrown.getMessage());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id, ticket_status) VALUES (1,null,'A23','econom','203$',1,'AVAILABLE')")
  void createTicketPlaceAlreadyAdded_fail() {
    EntityAlreadyExists thrown = assertThrows(EntityAlreadyExists.class, () -> {
      ticketService.addTicket(createTicketSaveRequest());
    }, "Ticket with this place on race already exists");
    assertEquals("Ticket with this place on race already exists", thrown.getMessage());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (2,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','AAAsssdDD',1,2)")
  @Sql(statements = "INSERT INTO ticket (user_id, place, place_class, cost, race_id, ticket_status) VALUES (null,'A23','econom','203$',2,'AVAILABLE')")
  void createTicketPlaceAlreadyAddedOnAnotherRace_success() {
    ticketService.addTicket(createTicketSaveRequest());
    var tickets = ticketH2Repository.findAll();
    assertEquals("A23", tickets.get(0).getPlace());
    assertEquals("A23", tickets.get(1).getPlace());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id,ticket_status) VALUES (1,null,'A22','econom','200$',1,'AVAILABLE')")
  void getTicket_success() {
    var foundTicket = ticketService.getTicket(1L);
    assertEquals("A22", foundTicket.getPlace());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id,ticket_status) VALUES (1,null,'A22','econom','200$',1,'AVAILABLE')")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id,ticket_status) VALUES (2,null,'A33','econom','200$',1,'AVAILABLE')")
  void getTickets_success() {
    var foundTickets = ticketService.getTickets();
    assertEquals(2, ticketH2Repository.findAll().size());
    assertEquals(2, foundTickets.size());
    assertEquals("A33", foundTickets.get(1).getPlace());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id,ticket_status) VALUES (1,null,'A22','econom','200$',1,'AVAILABLE')")
  void updateTicket_success() {
    assertEquals("A22", ticketService.getTicket(1L).getPlace());
    var expectedTicket = new TicketDto().setTicketStatus(TicketStatus.AVAILABLE).setCost("200$")
        .setPlace("A23").setPlaceClass("econom");
    var resultTicket = ticketService.updateTicket(createTicketUpdateRequest(), 1L);
    assertThat(resultTicket).usingRecursiveComparison().ignoringFields(Fields.id, Fields.racesDto)
        .isEqualTo(expectedTicket);
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id,ticket_status) VALUES (1,null,'B72','econom','200$',1,'AVAILABLE')")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id,ticket_status) VALUES (2,null,'A23','econom','200$',1,'AVAILABLE')")
  void updateTicketPlaceAlreadyAdded_fail() {
    EntityAlreadyExists thrown = assertThrows(EntityAlreadyExists.class, () -> {
      ticketService.updateTicket(createTicketUpdateRequest(), 1L);
    }, "Ticket with this place on race already exists");
    assertEquals("Ticket with this place on race already exists", thrown.getMessage());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (2,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id, ticket_status) VALUES (1,null,'A23','econom','203$',2,'AVAILABLE')")
  void updateTicketRaceNotFound_fail() {
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      ticketService.updateTicket(createTicketUpdateRequest(), 1L);
    }, "Race was not found");
    assertEquals("Race was not found", thrown.getMessage());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id,ticket_status) VALUES (1,null,'A22','econom','200$',1,'AVAILABLE')")
  void deleteTickets_success() {
    assertTrue(ticketH2Repository.findById(1L).isPresent());
    ticketService.deleteTicket(1L);
    assertFalse(ticketH2Repository.findById(1L).isPresent());
  }

  @Test
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (1,'Ivan','Baran','bara@gma.qew','+144568','keeker','1234','USER','ACTIVE')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id,ticket_status) VALUES (1,null,'A22','econom','200$',1,'AVAILABLE')")
  void buyTicket_success() {
    assertEquals(TicketStatus.AVAILABLE, ticketService.getTicket(1L).getTicketStatus());
    ticketService.buyTicket(1L, "keeker");
    assertEquals(TicketStatus.BOUGHT, ticketService.getTicket(1L).getTicketStatus());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id,ticket_status) VALUES (1,null,'A22','econom','200$',1,'AVAILABLE')")
  void buyTicketUserNotFound_fail() {
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      ticketService.buyTicket(1L, "Gordon");
    }, "User was not found");
    assertEquals("User was not found", thrown.getMessage());
  }

  @Test
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (1,'Ivan','Baran','bara@gma.qew','+144568','keeker','1234','USER','ACTIVE')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id,ticket_status) VALUES (1,null,'A22','econom','200$',1,'AVAILABLE')")
  void buyTicketTicketNotFound_fail() {
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      ticketService.buyTicket(2L, "keeker");
    }, "Ticket was not found by id");
    assertEquals("Ticket was not found by id", thrown.getMessage());
  }

  @Test
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (1,'Ivan','Baran','bara@gma.qew','+144568','keeker','1234','USER','ACTIVE')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id,ticket_status) VALUES (1,null,'A22','econom','200$',1,'BOOKED')")
  void buyTicketTicketReserved_fail() {
    TicketReservingException thrown = assertThrows(TicketReservingException.class, () -> {
      ticketService.buyTicket(1L, "keeker");
    }, "Ticket is not available");
    assertEquals("Ticket is not available", thrown.getMessage());
  }

  @Test
  @Sql(statements =
      "INSERT INTO users (id,name, surname, mail, phone, login, password, role, status) "
          + "VALUES (1,'Ivan','Baran','bara@gma.qew','+144568','keeker','1234','USER','ACTIVE')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id,ticket_status) VALUES (1,null,'A22','econom','200$',1,'AVAILABLE')")
  void bookTicket_success() {
    assertEquals(TicketStatus.AVAILABLE, ticketService.getTicket(1L).getTicketStatus());
    ticketService.bookTicket(1L, "keeker");
    assertEquals(TicketStatus.BOOKED, ticketService.getTicket(1L).getTicketStatus());
  }

  private TicketSaveRequest createTicketSaveRequest() {
    return new TicketSaveRequest().setTicketStatus(TicketStatus.AVAILABLE).setCost("200$")
        .setPlace("A23").setPlaceClass("econom").setRaceId(1L);
  }

  private TicketUpdateRequest createTicketUpdateRequest() {
    return new TicketUpdateRequest().setTicketStatus(TicketStatus.AVAILABLE).setCost("200$")
        .setUserId(null).setPlaceClass("econom").setPlace("A23").setRacesId(1L);
  }
}

package com.project.trav.service;

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
import com.project.trav.model.entity.Ticket;
import com.project.trav.model.entity.TicketStatus;
import com.project.trav.repository.TicketH2Repository;
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
  void createTicket() {
    ticketService.addTicket(
        new TicketSaveRequest().setTicketStatus(TicketStatus.AVAILABLE).setCost("$200")
            .setPlace("A23").setPlaceClass("econom").setRaceId(1L));
    assertEquals(1, ticketH2Repository.findAll().size());
    assertEquals("A23", ticketService.getTickets().get(0).getPlace());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  void createTicketRaceNotFoundFail() {
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      ticketService.addTicket(
          new TicketSaveRequest().setTicketStatus(TicketStatus.AVAILABLE).setCost("$200")
              .setPlace("A23").setPlaceClass("econom").setRaceId(2L));
    }, "Race was not fond");
    assertEquals("Race was not fond", thrown.getMessage());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id, ticket_status) VALUES (1,null,'A23','econom','203$',1,'AVAILABLE')")
  void createTicketPlaceAlreadyAddedFail() {
    EntityAlreadyExists thrown = assertThrows(EntityAlreadyExists.class, () -> {
      ticketService.addTicket(
          new TicketSaveRequest().setTicketStatus(TicketStatus.AVAILABLE).setCost("$200")
              .setPlace("A23").setPlaceClass("econom").setRaceId(1L));
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
  @Sql(statements = "INSERT INTO ticket (user_id, place, place_class, cost, race_id, ticket_status) VALUES (null,'A23','econom','203$',1,'AVAILABLE')")
  void createTicketPlaceAlreadyAddedOnAnotherRaceSuccess() {
      ticketService.addTicket(
          new TicketSaveRequest().setTicketStatus(TicketStatus.AVAILABLE).setCost("$200")
              .setPlace("A23").setPlaceClass("econom").setRaceId(2L));
      List<Ticket> tickets = ticketH2Repository.findAll();
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
  void getTicket() {
    TicketDto foundTicket = ticketService.getTicket(1L);
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
  void getTickets() {
    List<TicketDto> foundTickets = ticketService.getTickets();
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
  void updateTicket() {
    assertEquals("A22", ticketService.getTicket(1L).getPlace());
    ticketService.updateTicket(
        new TicketUpdateRequest().setTicketStatus(TicketStatus.AVAILABLE).setCost("200$")
            .setUserId(null).setPlaceClass("econom").setPlace("B12").setRacesId(1L), 1L);
    assertEquals("B12", ticketService.getTicket(1L).getPlace());
  }
  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id,ticket_status) VALUES (1,null,'A22','econom','200$',1,'AVAILABLE')")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id,ticket_status) VALUES (2,null,'B72','econom','200$',1,'AVAILABLE')")

  void updateTicketPlaceAlreadyAddedFail() {
    EntityAlreadyExists thrown = assertThrows(EntityAlreadyExists.class, () -> {
      ticketService.updateTicket(
          new TicketUpdateRequest().setTicketStatus(TicketStatus.AVAILABLE).setCost("$200")
              .setPlace("B72").setPlaceClass("econom").setRacesId(1L),1L);
    }, "Ticket with this place on race already exists");
    assertEquals("Ticket with this place on race already exists", thrown.getMessage());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id, ticket_status) VALUES (1,null,'A23','econom','203$',1,'AVAILABLE')")
  void updateTicketRaceNotFoundFail() {
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      ticketService.updateTicket(
          new TicketUpdateRequest().setTicketStatus(TicketStatus.AVAILABLE).setCost("$200")
              .setPlace("A23").setPlaceClass("econom").setRacesId(2L),1L);
    }, "Race was not fond");
    assertEquals("Race was not fond", thrown.getMessage());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (1,'Kiev','Ukraine','3.2m','Capital')")
  @Sql(statements = "INSERT INTO city (id,name, country, population, info) VALUES (2,'Berlin','Berlin','4.2m','Capital')")
  @Sql(statements =
      "INSERT INTO race (id, departure_date_time, arrival_date_time, duration, airline, race_number, departure_city_id, arrival_city_id) "
          + "VALUES (1,'2022-11-02T12:00:00','2022-11-02T13:00:00','3600000','Mau','fds1-233',1,2)")
  @Sql(statements = "INSERT INTO ticket (id,user_id, place, place_class, cost, race_id,ticket_status) VALUES (1,null,'A22','econom','200$',1,'AVAILABLE')")
  void deleteTickets() {
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
  void buyTicket() {
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
  void buyTicketUserNotFoundFail(){
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      ticketService.buyTicket(1L,"Gordon");
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
  void buyTicketTicketNotFoundFail(){
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      ticketService.buyTicket(2L,"keeker");
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
  void buyTicketTicketReservedFail(){
    TicketReservingException thrown = assertThrows(TicketReservingException.class, () -> {
      ticketService.buyTicket(1L,"keeker");
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
  void bookTicket() {
    assertEquals(TicketStatus.AVAILABLE, ticketService.getTicket(1L).getTicketStatus());
    ticketService.bookTicket(1L, "keeker");
    assertEquals(TicketStatus.BOOKED, ticketService.getTicket(1L).getTicketStatus());
  }
}

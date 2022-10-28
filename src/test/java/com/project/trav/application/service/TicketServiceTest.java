package com.project.trav.application.service;

import com.project.trav.application.services.TicketService;
import com.project.trav.domain.entity.City;
import com.project.trav.domain.entity.Race;
import com.project.trav.domain.entity.Ticket;
import com.project.trav.domain.entity.TicketStatus;
import com.project.trav.domain.repository.TicketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;
    @InjectMocks
    private TicketService ticketService;
    @Captor
    private ArgumentCaptor<Ticket> ticketArgumentCaptor;
    City city = new City().setId(1L).setName("Kiev").setCountry("Ukraine").setPopulation("2.7 million").setInformation("Capital");
    Race race = new Race()
            .setId(1L)
            .setDepartureCity("Kiev")
            .setArrivalCity("Berlin")
            .setDepartureTime(LocalTime.parse("12:00"))
            .setArrivalTime(LocalTime.parse("15:00"))
            .setTravelTime(LocalTime.parse("03:00"))
            .setAirline("Mau")
            .setRaceNumber("Wz-air-222")
            .setDepartureCityId(city)
            .setArrivalCityId(city);
    @Test
    void getRaces(){
        var ticketList  = Arrays.asList(
                new Ticket().setId(1L).setUserId(1L).setPlace("a23").setPlaceClass("econom")
                        .setCost("200").setTicketStatus(TicketStatus.AVAILABLE).setRaces(race),
                new Ticket().setId(1L).setUserId(1L).setPlace("a23").setPlaceClass("econom")
                        .setCost("200").setTicketStatus(TicketStatus.AVAILABLE).setRaces(race)
        );
        Mockito.when(ticketRepository.findAll()).thenReturn(ticketList);
        var expectedList = ticketService.getTickets();
        assertThat(expectedList).isEqualTo(ticketList);
    }
    @Test
    void getRace_success(){
        var sourceTicket = new Ticket().setId(1L).setUserId(1L).setPlace("a23").setPlaceClass("econom")
                .setCost("200").setTicketStatus(TicketStatus.AVAILABLE).setRaces(race);
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(sourceTicket));
        var expectedTicket = ticketService.getTicket(1L);
        assertThat(sourceTicket).isEqualTo(expectedTicket);
    }
    @Test
    void getRace_failure(){
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->ticketService.getTicket(1L));
    }
    @Test
    void deleteRace_success(){
        Mockito.when(ticketRepository.existsById(1L)).thenReturn(true);
        ticketService.deleteTicket(1L);
        Mockito.verify(ticketRepository).deleteById(1L);
    }
    @Test
    void deleteRace_failure(){
        Mockito.when(ticketRepository.existsById(1L)).thenReturn(false);
        String expectedMessage = "Ticket was not found by id";
        String actualMessage = Assertions.assertThrows(EntityNotFoundException.class,()->
                ticketService.deleteTicket(1L)).getMessage();
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }
    @Test
    void addRace(){
        var ticket = new Ticket().setId(1L).setUserId(1L).setPlace("a23").setPlaceClass("econom")
                .setCost("200").setTicketStatus(TicketStatus.AVAILABLE).setRaces(race);
        ticketService.addTicket(ticket);
        Mockito.verify(ticketRepository).save(ticket);
    }
    @Test
    void updateRace_success(){
        var sourceTicket =new Ticket().setId(1L).setUserId(1L).setPlace("a23").setPlaceClass("econom")
                .setCost("200").setTicketStatus(TicketStatus.AVAILABLE).setRaces(race);
        var expectedTicket = new Ticket().setId(1L).setUserId(1L).setPlace("a23").setPlaceClass("econom")
                .setCost("200").setTicketStatus(TicketStatus.AVAILABLE).setRaces(race);

        Mockito.when(ticketRepository.existsById(1L)).thenReturn(true);

        ticketService.updateTicket(sourceTicket,1L);
        Mockito.verify(ticketRepository).save(ticketArgumentCaptor.capture());
        assertThat(ticketArgumentCaptor.getValue()).isEqualTo(expectedTicket);
    }
    @Test
    void updateRace_failure(){
        var ticket = new Ticket().setId(1L).setUserId(1L).setPlace("a23").setPlaceClass("econom")
                .setCost("200").setTicketStatus(TicketStatus.AVAILABLE).setRaces(race);
        Mockito.when(ticketRepository.existsById(1L)).thenReturn(false);
        String expectedMessage = "Ticket was not found by id";
        String actualMessage = Assertions.assertThrows(EntityNotFoundException.class,()->
                ticketService.updateTicket(ticket,1L)).getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}

package com.project.trav.application.service;

import com.project.trav.mapper.TicketMapper;
import com.project.trav.model.dto.CityDto;
import com.project.trav.model.dto.RaceDto;
import com.project.trav.model.dto.TicketDto;
import com.project.trav.service.TicketService;
import com.project.trav.model.entity.City;
import com.project.trav.model.entity.Race;
import com.project.trav.model.entity.Ticket;
import com.project.trav.model.entity.TicketStatus;
import com.project.trav.repository.TicketRepository;
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
public class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private TicketMapper ticketMapper;
    @InjectMocks
    private TicketService ticketService;
    City city = new City().setId(1L).setName("Kiev").setCountry("Ukraine").setPopulation("2.7 million").setInformation("Capital");
    Race race = new Race().setTravelTime("1").setAirline("Mau").setRaceNumber("Wr23-ww").setDepartureCityId(city).setArrivalCityId(city);
    CityDto cityDto = new CityDto().setId(1L).setName("Kiev").setCountry("Ukraine").setPopulation("2.7 million").setInformation("Capital");
    RaceDto raceDto = new RaceDto().setTravelTime("1").setAirline("Mau").setRaceNumber("Wr23-ww").setDepartureCityIdDto(cityDto).setArrivalCityIdDto(cityDto);
    @Test
    void getRaces(){
        var ticketList  = Arrays.asList(
                new TicketDto().setId(1L).setUserId(1L).setPlace("a23").setPlaceClass("econom")
                        .setCost("200").setTicketStatus(TicketStatus.AVAILABLE).setRacesDto(raceDto),
                new TicketDto().setId(1L).setUserId(1L).setPlace("a23").setPlaceClass("econom")
                        .setCost("200").setTicketStatus(TicketStatus.AVAILABLE).setRacesDto(raceDto)
        );
        Mockito.when(ticketMapper.toTicketDtos(Mockito.anyList())).thenReturn(ticketList);
        var expectedList = ticketService.getTickets();
        assertThat(expectedList).isEqualTo(ticketList);
    }
    @Test
    void getRace_success(){
        var sourceTicket = new Ticket().setId(1L).setUserId(1L).setPlace("a23").setPlaceClass("econom")
                .setCost("200").setTicketStatus(TicketStatus.AVAILABLE).setRaces(race);
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(sourceTicket));
        var expectedTicket = ticketService.getTicket(1L);
        assertThat(ticketMapper.toTicketDto(sourceTicket)).isEqualTo(expectedTicket);
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
        var ticket = new TicketDto().setId(1L).setUserId(1L).setPlace("a23").setPlaceClass("econom")
                .setCost("200").setTicketStatus(TicketStatus.AVAILABLE).setRacesDto(raceDto);
        ticketService.addTicket(ticket);
        Mockito.verify(ticketRepository).save(ticketMapper.toTicket(ticket));
    }
    @Test
    void updateRace_success(){
        var sourceTicket =new TicketDto().setId(1L).setUserId(1L).setPlace("a23").setPlaceClass("econom")
                .setCost("200").setTicketStatus(TicketStatus.AVAILABLE).setRacesDto(raceDto);

        Mockito.when(ticketRepository.existsById(1L)).thenReturn(true);

        ticketService.updateTicket(sourceTicket,1L);
        Mockito.verify(ticketRepository).save(ticketMapper.toTicket(sourceTicket));
    }
    @Test
    void updateRace_failure(){
        var ticketDto = new TicketDto().setId(1L).setUserId(1L).setPlace("a23").setPlaceClass("econom")
                .setCost("200").setTicketStatus(TicketStatus.AVAILABLE).setRacesDto(raceDto);
        Mockito.when(ticketRepository.existsById(1L)).thenReturn(false);
        String expectedMessage = "Ticket was not found by id";
        String actualMessage = Assertions.assertThrows(EntityNotFoundException.class,()->
                ticketService.updateTicket(ticketDto,1L)).getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}

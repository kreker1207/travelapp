package com.project.trav.application.service;

import com.project.trav.application.services.TicketService;
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
import java.util.Arrays;
import java.util.List;
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
    Race race = new Race(1L,"12:00","13:00","Kiev",
            "Berlin","1","Mau","Wr23-ww");
    @Test
    void getRaces(){
        List<Ticket> ticketList  = Arrays.asList(
                new Ticket(1L,1L,"A23","econom",
                        "200",TicketStatus.AVAILABLE,race),
                new Ticket(1L,1L,"A23","econom",
                        "200",TicketStatus.AVAILABLE,race)
        );
        Mockito.when(ticketRepository.findAll()).thenReturn(ticketList);
        List<Ticket> expectedList = ticketService.getTickets();
        assertThat(expectedList).isEqualTo(ticketList);
    }
    @Test
    void getRace_success(){
        Ticket sourceTicket = new Ticket(1L,1L,"A23","econom",
                "200", TicketStatus.AVAILABLE,race);
        Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(sourceTicket));
        Ticket expectedTicket = ticketService.getTicket(1L);
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
        Ticket ticket = new Ticket(1L,1L,"A23","econom",
                "200",TicketStatus.AVAILABLE,race);
        ticketService.addTicket(ticket);
        Mockito.verify(ticketRepository).save(ticket);
    }
    @Test
    void updateRace_success(){
        Ticket sourceTicket =new Ticket(1L,1L,"A23","econom",
                "200",TicketStatus.AVAILABLE,race);
        Ticket expectedTicket = new Ticket(1L,1L,"A23","econom",
                "200",TicketStatus.AVAILABLE,race);

        Mockito.when(ticketRepository.existsById(1L)).thenReturn(true);

        ticketService.updateTicket(sourceTicket,1L);
        Mockito.verify(ticketRepository).save(ticketArgumentCaptor.capture());
        assertThat(ticketArgumentCaptor.getValue()).isEqualTo(expectedTicket);
    }
    @Test
    void updateRace_failure(){
        Ticket ticket = new Ticket(1L,1L,"A23","econom",
                "200",TicketStatus.AVAILABLE,race);
        Mockito.when(ticketRepository.existsById(1L)).thenReturn(false);
        String expectedMessage = "Ticket was not found by id";
        String actualMessage = Assertions.assertThrows(EntityNotFoundException.class,()->
                ticketService.updateTicket(ticket,1L)).getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}

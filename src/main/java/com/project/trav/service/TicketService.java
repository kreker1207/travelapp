package com.project.trav.service;

import com.project.trav.mapper.TicketMapper;
import com.project.trav.model.dto.TicketDto;
import com.project.trav.model.entity.Race;
import com.project.trav.model.entity.Ticket;
import com.project.trav.model.entity.TicketStatus;
import com.project.trav.model.entity.User;
import com.project.trav.repository.TicketRepository;
import com.project.trav.repository.UserRepository;
import com.project.trav.exeption.EntityNotFoundByIdException;
import com.project.trav.exeption.TicketReservingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

  private final TicketMapper ticketMapper;
  private final UserRepository userRepository;
  private final TicketRepository ticketRepository;
  private static final String NOT_FOUND_ERROR = "Ticket was not found by id";

  public List<TicketDto> getTickets() {
    return ticketMapper.toTicketDtos(ticketRepository.findAll());
  }

  public TicketDto getTicket(Long id) {
    return ticketMapper.toTicketDto(findByIdTicket(id));
  }
  public void addTicket(TicketDto ticketDto) {
    ticketRepository.save(ticketMapper.toTicket(ticketDto));
  }

  public void deleteTicket(Long id) {
    findByIdTicket(id);
    ticketRepository.deleteById(id);
  }

  public void updateTicket(TicketDto ticketDto, Long id) {
    Ticket oldTicket = findByIdTicket(id);
    ticketRepository.save(oldTicket.setId(id).setUserId(ticketDto.getUserId())
        .setPlace(ticketDto.getPlace()).setPlaceClass(ticketDto.getPlaceClass()).setCost(ticketDto.getCost())
        .setTicketStatus(ticketDto.getTicketStatus()).setRaces(new Race().setId(ticketDto.getId())));
  }

  public void buyTicket(Long ticketId, String username) {
    prepareTicket(TicketStatus.BOUGHT, ticketId, username);
  }

  public void bookTicket(Long ticketId, String username) {
    prepareTicket(TicketStatus.BOOKED, ticketId, username);
  }
  private Ticket findByIdTicket(Long id) {
    return ticketRepository.findById(id).orElseThrow(() -> {
      throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
    });

  }
  private void prepareTicket(TicketStatus ticketStatus, Long ticketId, String username) {
    User user = userRepository.findByLogin(username).orElseThrow(() ->
        new EntityNotFoundByIdException("User was not found"));
    Ticket ticket = findByIdTicket(ticketId);
    if (!ticket.getTicketStatus().equals(TicketStatus.AVAILABLE)) {
      throw new TicketReservingException("Ticket is not available");
    }
    ticket.setUserId(user.getId());
    ticket.setTicketStatus(ticketStatus);
    updateTicket(ticketMapper.toTicketDto(ticket), ticketId);
  }
}

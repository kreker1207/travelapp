package com.project.trav.service;

import com.project.trav.exeption.EntityAlreadyExists;
import com.project.trav.mapper.TicketMapper;
import com.project.trav.model.dto.TicketDto;
import com.project.trav.model.dto.TicketSaveRequest;
import com.project.trav.model.dto.TicketUpdateRequest;
import com.project.trav.model.entity.Race;
import com.project.trav.model.entity.Ticket;
import com.project.trav.model.entity.TicketStatus;
import com.project.trav.model.entity.User;
import com.project.trav.repository.RaceRepository;
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
  private final RaceRepository raceRepository;
  private final TicketRepository ticketRepository;
  private static final String NOT_FOUND_ERROR = "Ticket was not found by id";

  public List<TicketDto> getTickets() {
    return ticketMapper.toTicketDtos(ticketRepository.findAll());
  }

  public TicketDto getTicket(Long id) {
    return ticketMapper.toTicketDto(findByIdTicket(id));
  }

  public TicketDto addTicket(TicketSaveRequest ticketSaveRequest) {
    validPlaceAdd(ticketSaveRequest);
    Ticket ticket = new Ticket().setPlace(ticketSaveRequest.getPlace()).setPlaceClass(ticketSaveRequest.getPlaceClass())
            .setCost(ticketSaveRequest.getCost()).setRaces(raceRepository.findById(ticketSaveRequest.getRaceId()).get())
            .setTicketStatus(ticketSaveRequest.getTicketStatus());
    ticketRepository.save(ticket);
    return ticketMapper.toTicketDto(ticket);
  }

  public TicketDto deleteTicket(Long id) {
    Ticket ticket = findByIdTicket(id);
    ticketRepository.deleteById(id);
    return ticketMapper.toTicketDto(ticket);
  }

  public TicketDto updateTicket(TicketUpdateRequest ticketUpdateRequest, Long id) {
    findByIdTicket(id);
    Ticket oldTicket = ticketMapper.toTicket(getTicket(id));
    validPlaceUpdate(ticketUpdateRequest, oldTicket);
    Race race = raceRepository.findById(ticketUpdateRequest.getRacesId()).orElseThrow(() ->
        new EntityNotFoundByIdException("Race not found"));
    ticketRepository.save(oldTicket
        .setId(id)
        .setPlace(ticketUpdateRequest.getPlace())
        .setPlaceClass(ticketUpdateRequest.getPlaceClass())
        .setCost(ticketUpdateRequest.getCost())
        .setTicketStatus(ticketUpdateRequest.getTicketStatus())
        .setRaces(new Race().setId(race.getId()).setRaceNumber(race.getRaceNumber())
            .setAirline(race.getAirline())
            .setDepartureDateTime(race.getDepartureDateTime())
            .setTravelTimeDuration(race.getTravelTimeDuration())
            .setArrivalDateTime(race.getArrivalDateTime())
            .setDepartureCity(race.getDepartureCity()).setArrivalCity(race.getArrivalCity())));
    return ticketMapper.toTicketDto(oldTicket);
  }

  public TicketDto buyTicket(Long ticketId, String username) {
    return ticketMapper.toTicketDto(prepareTicket(TicketStatus.BOUGHT, ticketId, username));
  }

  public TicketDto bookTicket(Long ticketId, String username) {
    return ticketMapper.toTicketDto(prepareTicket(TicketStatus.BOOKED, ticketId, username));
  }

  private Ticket findByIdTicket(Long id) {
    return ticketRepository.findById(id).orElseThrow(() -> {
      throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
    });

  }

  private Ticket prepareTicket(TicketStatus ticketStatus, Long ticketId, String username) {
    User user = userRepository.findByLogin(username).orElseThrow(() ->
        new EntityNotFoundByIdException("User was not found"));
    Ticket ticket = findByIdTicket(ticketId);
    if (!ticket.getTicketStatus().equals(TicketStatus.AVAILABLE)) {
      throw new TicketReservingException("Ticket is not available");
    }
    ticket.setUserId(user.getId());
    ticket.setTicketStatus(ticketStatus);
    ticketRepository.save(ticket);
    return ticket;
  }

  private void validPlaceUpdate(TicketUpdateRequest ticketUpdateRequest, Ticket ticket) {
    Race race = raceRepository.findById(ticketUpdateRequest.getRacesId()).orElseThrow(() ->
        new EntityNotFoundByIdException("Race was not fond"));
    if (!ticketUpdateRequest.getPlace().equals(ticket.getPlace())
        && ticketRepository.findByPlaceAndRaces_RaceNumber(ticketUpdateRequest.getPlace(),
            race.getRaceNumber())
        .isPresent()) {
      throw new EntityAlreadyExists("Ticket with this place on race already exists");
    }
  }

  private void validPlaceAdd(TicketSaveRequest ticketSaveRequest) {
    Race race = raceRepository.findById(ticketSaveRequest.getRaceId()).orElseThrow(() ->
        new EntityNotFoundByIdException("Race was not fond"));
    if (ticketRepository.findByPlaceAndRaces_RaceNumber(ticketSaveRequest.getPlace(), race.getRaceNumber())
        .isPresent()) {
      throw new EntityAlreadyExists("Ticket with this place on race already exists");
    }
  }
}

package com.project.trav.service;

import com.project.trav.exeption.EntityAlreadyExists;
import com.project.trav.mapper.TicketMapper;
import com.project.trav.model.dto.TicketDto;
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

  public void addTicket(TicketDto ticketDto) {
    validPlaceAdd(ticketDto);
    ticketRepository.save(ticketMapper.toTicket(ticketDto));
  }

  public void deleteTicket(Long id) {
    findByIdTicket(id);
    ticketRepository.deleteById(id);
  }

  public void updateTicket(TicketUpdateRequest ticketUpdateRequest, Long id) {
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
    ticketRepository.save(ticket);
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

  private void validPlaceAdd(TicketDto ticketDto) {
    Race race = raceRepository.findById(ticketDto.getRacesDto().getId()).orElseThrow(() ->
        new EntityNotFoundByIdException("Race was not fond"));
    if (ticketRepository.findByPlaceAndRaces_RaceNumber(ticketDto.getPlace(), race.getRaceNumber())
        .isPresent()) {
      throw new EntityAlreadyExists("Ticket with this place on race already exists");
    }
  }
}

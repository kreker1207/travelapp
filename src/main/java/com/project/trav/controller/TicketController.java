package com.project.trav.controller;

import com.project.trav.model.dto.TicketUpdateRequest;
import com.project.trav.service.TicketService;
import com.project.trav.model.dto.TicketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tickets")
public class TicketController {

  private final TicketService ticketService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public List<TicketDto> getTickets() {
    return ticketService.getTickets();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public TicketDto getTicket(@PathVariable Long id) {
    return ticketService.getTicket(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('admins')")
  public TicketDto addTicket(@Valid @RequestBody TicketDto ticketDto) {
    return ticketService.addTicket(ticketDto);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAuthority('admins')")
  public TicketDto updateTicket(@Valid @RequestBody TicketUpdateRequest ticketUpdateRequest,
      @PathVariable Long id) {
    return ticketService.updateTicket(ticketUpdateRequest, id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAuthority('admins')")
  public TicketDto deleteTicket(@PathVariable Long id) {
    return ticketService.deleteTicket(id);
  }

  @PutMapping("buy/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public TicketDto buyTicket(@PathVariable Long id, HttpServletRequest request) {
    String username = request.getUserPrincipal().getName();
    return ticketService.buyTicket(id, username);
  }

  @PutMapping("book/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public TicketDto bookTicket(@PathVariable Long id, HttpServletRequest request) {
    String username = request.getUserPrincipal().getName();
    return ticketService.bookTicket(id, username);
  }
}

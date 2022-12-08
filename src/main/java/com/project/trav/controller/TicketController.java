package com.project.trav.controller;

import static com.project.trav.configuration.SecurityConfiguration.SECURITY_CONFIG_NAME;

import com.project.trav.model.dto.TicketSaveRequest;
import com.project.trav.model.dto.TicketUpdateRequest;
import com.project.trav.service.TicketService;
import com.project.trav.model.dto.TicketDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/v1/tickets")
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class TicketController {

  private final TicketService ticketService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get all tickets", responses = {
      @ApiResponse(responseCode = "200", description = "Tickets were found",
          content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TicketDto.class)))
          }),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public List<TicketDto> getTickets() {
    return ticketService.getTickets();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get ticket by id", responses = {
      @ApiResponse(responseCode = "200", description = "Ticket was found by id",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDto.class))
          }),
      @ApiResponse(responseCode = "404", description = "Ticket was not found by id", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public TicketDto getTicket(@PathVariable Long id) {
    return ticketService.getTicket(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Add new ticket", responses = {
      @ApiResponse(responseCode = "200", description = "Ticket was created",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDto.class))
          }),
      @ApiResponse(responseCode = "400", description = "Bad request check fields", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAuthority('admins')")
  public TicketDto addTicket(@Valid @RequestBody TicketSaveRequest ticketSaveRequest) {
    return ticketService.addTicket(ticketSaveRequest);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Update ticket", responses = {
      @ApiResponse(responseCode = "200", description = "Ticket was updated",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDto.class))
          }),
      @ApiResponse(responseCode = "400", description = "Bad request check fields", content = @Content),
      @ApiResponse(responseCode = "404", description = "Ticket was not found by id", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAuthority('admins')")
  public TicketDto updateTicket(@Valid @RequestBody TicketUpdateRequest ticketUpdateRequest,
      @PathVariable Long id) {
    return ticketService.updateTicket(ticketUpdateRequest, id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Delete ticket by id", responses = {
      @ApiResponse(responseCode = "200", description = "Ticket was delete",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDto.class))
          }),
      @ApiResponse(responseCode = "404", description = "Ticket was not found by id", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAuthority('admins')")
  public TicketDto deleteTicket(@PathVariable Long id) {
    return ticketService.deleteTicket(id);
  }

  @PutMapping("buy/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Ticket was bought on your account", responses = {
      @ApiResponse(responseCode = "200", description = "Ticket was bought",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDto.class))
          }),
      @ApiResponse(responseCode = "404", description = "Ticket was not found by id", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public TicketDto buyTicket(@PathVariable Long id, HttpServletRequest request) {
    String username = request.getUserPrincipal().getName();
    return ticketService.buyTicket(id, username);
  }

  @PutMapping("book/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Ticket was booked on your account", responses = {
      @ApiResponse(responseCode = "200", description = "Ticket was booked",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDto.class))
          }),
      @ApiResponse(responseCode = "404", description = "Ticket was not found by id", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public TicketDto bookTicket(@PathVariable Long id, HttpServletRequest request) {
    String username = request.getUserPrincipal().getName();
    return ticketService.bookTicket(id, username);
  }
}

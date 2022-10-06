package com.project.trav.ifrastructure.rest;

import com.project.trav.application.services.TicketService;
import com.project.trav.ifrastructure.dto.TicketDto;
import com.project.trav.ifrastructure.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/1.0/tickets")
public class TicketController {
    private final TicketMapper ticketMapper;
    private final TicketService ticketService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TicketDto> getTickets(){return ticketMapper.toTicketDtos(ticketService.getTickets());}

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TicketDto getTicket(@PathVariable Long id){return ticketMapper.toTicketDto(ticketService.getTicket(id));}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTicket(@RequestBody TicketDto ticketDto){ticketService.addTicket(ticketMapper.toTicket(ticketDto));}

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTicket(@PathVariable Long id,@RequestBody TicketDto ticketDto){ticketService.updateTicket(ticketMapper.toTicket(ticketDto),id);}

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable Long id){ticketService.deleteTicket(id);}
}

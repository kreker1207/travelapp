package com.project.trav.application.services;

import com.project.trav.domain.entity.Ticket;
import com.project.trav.domain.repository.TicketRepository;
import com.project.trav.exeption.EntityNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private static final String NOT_FOUND_ERROR ="Ticket was not found by id";

    public List<Ticket> getTickets(){return ticketRepository.findAll();}

    public Ticket getTicket(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        });
    }
    public void addTicket(Ticket ticket){ticketRepository.save(ticket);}

    public void deleteTicket(Long id){
        if(!ticketRepository.existsById(id)){
        throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
    }
        ticketRepository.deleteById(id);
    }
    public void updateTicket(Ticket ticket, Long id){
        if(!ticketRepository.existsById(id)){
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        }
        ticketRepository.save(ticket);
    }
}

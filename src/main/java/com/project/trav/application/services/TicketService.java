package com.project.trav.application.services;

import com.project.trav.domain.entity.Ticket;
import com.project.trav.domain.entity.TicketStatus;
import com.project.trav.domain.entity.User;
import com.project.trav.domain.repository.TicketRepository;
import com.project.trav.domain.repository.UserRepository;
import com.project.trav.exeption.EntityNotFoundByIdException;
import com.project.trav.exeption.TicketReservingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final UserRepository userRepository;
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
    public void buyTicket(Long ticketId, String username){
        User user = userRepository.findByLogin(username).orElseThrow(()->
                new EntityNotFoundByIdException("User was not found"));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->
                new EntityNotFoundByIdException(NOT_FOUND_ERROR));
        if (!ticket.getTicketStatus().equals(TicketStatus.AVAILABLE)){
            throw new TicketReservingException("Ticket is not available");
        }
        ticket.setUserId(user.getId());
        ticket.setTicketStatus(TicketStatus.BOUGHT);
        updateTicket(ticket,ticketId);
    }
    public void bookTicket(Long ticketId,String username){
        User user = userRepository.findByLogin(username).orElseThrow(()->
                new EntityNotFoundByIdException("User was not found"));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->
                new EntityNotFoundByIdException(NOT_FOUND_ERROR));
        if (!ticket.getTicketStatus().equals(TicketStatus.AVAILABLE)){
            throw new TicketReservingException("Ticket is not available");
        }
        ticket.setUserId(user.getId());
        ticket.setTicketStatus(TicketStatus.BOOKED);
        updateTicket(ticket,ticketId);
    }
}

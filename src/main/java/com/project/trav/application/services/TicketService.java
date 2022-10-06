package com.project.trav.application.services;

import com.project.trav.domain.entity.Ticket;
import com.project.trav.domain.repository.TicketRepository;
import com.project.trav.exeption.EntityNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private static final String TICKET_ERROR_TEMPLATE ="Ticket was not found by id";

    public List<Ticket> getTickets(){return ticketRepository.findAll();}

    public Ticket getTicket(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundByIdException(TICKET_ERROR_TEMPLATE);
        });
    }
    public void addTicket(Ticket ticket){ticketRepository.save(ticket);}

    public void deleteTicket(Long id){
        if(!ticketRepository.existsById(id)){
        throw new EntityNotFoundByIdException(TICKET_ERROR_TEMPLATE);
    }
        ticketRepository.deleteById(id);
    }
    public void updateTicket(Ticket ticket, Long id){
        if(!ticketRepository.existsById(id)){
            throw new EntityNotFoundByIdException(TICKET_ERROR_TEMPLATE);
        }
        Ticket oldTicket = getTicket(id);
        ticketRepository.save(Ticket.builder()
                .id(id)
                .userId(Objects.isNull(ticket.getUserId())?oldTicket.getUserId():ticket.getUserId())
                .place(Objects.isNull(ticket.getPlace())?oldTicket.getPlace():ticket.getPlace())
                .placeClass(Objects.isNull(ticket.getPlaceClass())?oldTicket.getPlaceClass():ticket.getPlaceClass())
                .cost(Objects.isNull(ticket.getCost())?oldTicket.getCost():ticket.getCost())
                .build());
    }
}

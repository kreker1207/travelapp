package com.project.trav.ifrastructure.mapper;

import com.project.trav.domain.entity.Ticket;
import com.project.trav.ifrastructure.dto.TicketDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = RaceMapper.class)
public interface TicketMapper {
    TicketDto toTicketDto(Ticket ticket);
    List<TicketDto> toTicketDtos(List<Ticket> tickets);
    Ticket toTicket(TicketDto ticketDto);
}

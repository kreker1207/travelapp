package com.project.trav.mapper;

import com.project.trav.model.entity.Ticket;
import com.project.trav.model.dto.TicketDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = RaceMapper.class)
public interface TicketMapper {
    @Mapping(target = "racesDto",source = "races")
    TicketDto toTicketDto(Ticket ticket);

    List<TicketDto> toTicketDtos(List<Ticket> tickets);
    @Mapping(target = "races",source = "racesDto")
    Ticket toTicket(TicketDto ticketDto);
}

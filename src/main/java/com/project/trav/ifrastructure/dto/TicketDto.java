package com.project.trav.ifrastructure.dto;

import com.project.trav.domain.entity.Race;
import com.project.trav.domain.entity.TicketStatus;
import lombok.Data;

@Data
public class TicketDto {
    private Long id;
    private Long userId;
    private String place;
    private String placeClass;
    private String cost;
    private TicketStatus ticketStatus;
    private Race races;
}

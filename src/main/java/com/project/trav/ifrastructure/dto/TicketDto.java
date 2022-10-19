package com.project.trav.ifrastructure.dto;

import com.project.trav.domain.entity.Race;
import com.project.trav.domain.entity.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TicketDto {
    private Long id;
    private Long userId;
    @NonNull
    private String place;
    @NonNull
    private String placeClass;
    @NonNull
    private String cost;
    @NonNull
    private TicketStatus ticketStatus;
    private Race races;
}

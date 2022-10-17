package com.project.trav.ifrastructure.dto;

import com.project.trav.domain.entity.Race;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private Race races;

}

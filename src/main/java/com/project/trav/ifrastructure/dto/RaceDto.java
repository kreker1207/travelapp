package com.project.trav.ifrastructure.dto;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceDto {
    private Long id;
    @NonNull
    private String departureTime;
    @NonNull
    private String arrivalTime;
    @NonNull
    private String departureCity;
    @NonNull
    private String arrivalCity;
    @NonNull
    private String travelTime;
    @NonNull
    private String airline;
    @NonNull
    private String raceNumber;
}

package com.project.trav.ifrastructure.dto;

import lombok.Data;

@Data
public class RaceDto {
    private Long id;
    private String departureTime;
    private String arrivalTime;
    private String departureCity;
    private String arrivalCity;
    private String travelTime;
    private String airline;
}

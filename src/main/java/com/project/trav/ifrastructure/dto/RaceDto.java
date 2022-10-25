package com.project.trav.ifrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
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
    private CityDto departureCityIdDto;
    private CityDto arrivalCityIdDto;
}

package com.project.trav.ifrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RaceDto {
    private Long id;
    @NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime departureTime;
    @NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime arrivalTime;
    @NonNull
    private String departureCity;
    @NonNull
    private String arrivalCity;
    @NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime travelTime;
    @NonNull
    private String airline;
    @NonNull
    @Size(min = 3, max = 12)
    private String raceNumber;
    private CityDto departureCityIdDto;
    private CityDto arrivalCityIdDto;
}

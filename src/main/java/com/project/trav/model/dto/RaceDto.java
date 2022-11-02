package com.project.trav.model.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RaceDto {

  private Long id;
  @NotNull
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime departureTime;
  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime arrivalTime;
  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalTime travelTime;
  @NotNull
  private String airline;
  @NotNull
  @Size(min = 3, max = 20)
  private String raceNumber;
  @NotNull
  private CityDto departureCityIdDto;
  @NotNull
  private CityDto arrivalCityIdDto;
}

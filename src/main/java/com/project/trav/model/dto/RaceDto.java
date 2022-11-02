package com.project.trav.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RaceDto {

  private Long id;
  @NotNull
  private String departureTime;
  @NotNull
  private String arrivalTime;
  @NotNull
  private String travelTime;
  @NotNull
  private String airline;
  @NotNull
  private String raceNumber;
  @NotNull
  private CityDto departureCityIdDto;
  @NotNull
  private CityDto arrivalCityIdDto;
}

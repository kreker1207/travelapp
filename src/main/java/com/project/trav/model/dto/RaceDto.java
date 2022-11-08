package com.project.trav.model.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
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
  private List<TicketDto> ticketDtoList;
  @NotNull
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime departureDateTime;
  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime arrivalDateTime;
  private Duration travelTimeDuration;
  @NotNull
  private String airline;
  @NotNull
  @Size(min = 3, max = 20)
  private String raceNumber;
  @NotNull
  private CityDto departureCityDto;
  @NotNull
  private CityDto arrivalCityDto;
}

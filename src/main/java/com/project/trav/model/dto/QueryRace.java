package com.project.trav.model.dto;

import com.querydsl.core.types.EntityPath;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryRace{
  private Long id;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime departureDateTime;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime arrivalDateTime;
  private Duration travelTimeDuration;
  private String airline;
  @Size(min = 3, max = 20)
  private String raceNumber;
  private String departureCityName;
  private String arrivalCityName;
}

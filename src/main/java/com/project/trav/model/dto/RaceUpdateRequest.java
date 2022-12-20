package com.project.trav.model.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RaceUpdateRequest {
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime departureDateTime;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime arrivalDateTime;
  private String airline;
  @Size(min = 3, max = 20)
  private String raceNumber;
  private Long departureCityId;
  private Long arrivalCityId;
}

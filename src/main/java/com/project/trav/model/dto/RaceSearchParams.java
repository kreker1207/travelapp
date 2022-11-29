package com.project.trav.model.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RaceSearchParams {
  private LocalDateTime departureDateTime;
  private LocalDateTime arrivalDateTime;
  private String airline;
  private String raceNumber;
}

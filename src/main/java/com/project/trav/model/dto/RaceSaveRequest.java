package com.project.trav.model.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RaceSaveRequest {
  @NotNull
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
  private LocalDateTime departureDateTime;
  @NotNull
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
  private LocalDateTime arrivalDateTime;
  @NotNull
  private String airline;
  @NotNull
  @Size(min = 3, max = 20)
  private String raceNumber;
  @NotNull
  private Long departureCityId;
  @NotNull
  private Long arrivalCityId;
}

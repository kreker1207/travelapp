package com.project.trav.model.dto;

import com.project.trav.model.entity.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TicketDto {

  private Long id;
  private Long userId;
  @NotNull
  private String place;
  @NotNull
  private String placeClass;
  @NotNull
  private String cost;
  @NotNull
  private TicketStatus ticketStatus;
  @NotNull
  private RaceDto racesDto;
}

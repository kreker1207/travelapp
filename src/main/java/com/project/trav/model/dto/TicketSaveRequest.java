package com.project.trav.model.dto;

import com.project.trav.model.entity.TicketStatus;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TicketSaveRequest {
  @NotNull
  private String place;
  @NotNull
  private String placeClass;
  @NotNull
  private String cost;
  @NotNull
  private TicketStatus ticketStatus;
  @NotNull
  private Long racesId;
}

package com.project.trav.model.dto;

import com.project.trav.model.entity.TicketStatus;;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TicketUpdateRequest {

  private Long userId;
  private String place;
  private String placeClass;
  private String cost;
  private TicketStatus ticketStatus;
  private Long racesId;
}

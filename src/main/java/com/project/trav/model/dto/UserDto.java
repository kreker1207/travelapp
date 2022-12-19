package com.project.trav.model.dto;

import com.project.trav.model.entity.Role;
import com.project.trav.model.entity.Status;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;
import lombok.experimental.FieldNameConstants;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@FieldNameConstants
public class UserDto {

  private Long id;
  @NotNull
  private String name;
  @NotNull
  private String surname;
  @NotNull
  @Size(min = 6, max = 50)
  private String mail;
  @NotNull
  private String phone;
  @NotNull
  private String login;
  @NotNull
  @Size(min = 4, max = 18)
  private String password;
  @NotNull
  private Role role;
  @NotNull
  private Status status;
  private List<TicketDto> ticketsDto;
}

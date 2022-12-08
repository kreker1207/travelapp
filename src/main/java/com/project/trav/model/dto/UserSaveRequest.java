package com.project.trav.model.dto;

import com.project.trav.model.entity.Role;
import com.project.trav.model.entity.Status;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserSaveRequest {
  @NotNull
  private String name;
  @NotNull
  private String surname;
  @NotNull
  private String mail;
  @NotNull
  private String phone;
  @NotNull
  private String login;
  @NotNull
  private String password;
  @NotNull
  private Role role;
  @NotNull
  private Status status;
}

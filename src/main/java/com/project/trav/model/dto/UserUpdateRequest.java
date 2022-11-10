package com.project.trav.model.dto;

import com.project.trav.model.entity.Role;
import com.project.trav.model.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserUpdateRequest {
  private String name;
  private String surname;
  private String mail;
  private String phone;
  private String login;
  private Role role;
  private Status status;
}

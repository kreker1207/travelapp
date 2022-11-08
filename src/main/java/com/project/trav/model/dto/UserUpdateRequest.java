package com.project.trav.model.dto;

import com.project.trav.model.entity.Role;
import com.project.trav.model.entity.Status;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserUpdateRequest {
  private Long id;
  private String name;
  private String surname;
  @Size(min = 6, max = 50)
  private String mail;
  private String phone;
  private String login;
  @Size(min = 4, max = 70)
  private String password;
  private Role role;
  private Status status;
}

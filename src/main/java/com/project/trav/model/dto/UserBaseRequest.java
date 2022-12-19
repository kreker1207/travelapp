package com.project.trav.model.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserBaseRequest {
  @NotNull
  private String mail;
  @NotNull
  private String phone;
  @NotNull
  private String login;
}

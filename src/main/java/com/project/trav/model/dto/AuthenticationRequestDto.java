package com.project.trav.model.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

  private String login;
  private String password;
}

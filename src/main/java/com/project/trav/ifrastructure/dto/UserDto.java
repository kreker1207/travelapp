package com.project.trav.ifrastructure.dto;

import com.project.trav.domain.entity.Ticket;
import lombok.Data;

import java.util.List;
@Data
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String mail;
    private String phone;
    private String login;
    private String password;
    private List<Ticket> tickets;
}

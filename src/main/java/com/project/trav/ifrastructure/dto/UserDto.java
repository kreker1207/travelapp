package com.project.trav.ifrastructure.dto;

import com.project.trav.domain.entity.Role;
import com.project.trav.domain.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDto {
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private String mail;
    @NonNull
    private String phone;
    @NonNull
    private String login;
    @NonNull
    private String password;
    @NonNull
    private Role role;
    @NonNull
    private Status status;
    private List<TicketDto> ticketsDto;
}

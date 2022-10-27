package com.project.trav.domain.entity;

import com.project.trav.ifrastructure.dto.FindParamDto;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class UserRequest {
    private String login;
    private String mail;
    private FindParamDto findParamDto;
}

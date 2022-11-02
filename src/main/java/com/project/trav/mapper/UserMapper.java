package com.project.trav.mapper;

import com.project.trav.model.entity.User;
import com.project.trav.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = TicketMapper.class)
public interface UserMapper {

  @Mapping(target = "ticketsDto", source = "tickets")
  UserDto toUserDto(User user);

  List<UserDto> toUserDtos(List<User> users);

  @Mapping(target = "tickets", source = "ticketsDto")
  User toUser(UserDto userDto);
}

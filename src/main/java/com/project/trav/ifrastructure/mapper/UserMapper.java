package com.project.trav.ifrastructure.mapper;

import com.project.trav.domain.entity.User;
import com.project.trav.ifrastructure.dto.UserDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = TicketMapper.class)
public interface UserMapper {
    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> users);

    User toUser(UserDto userDto);
}

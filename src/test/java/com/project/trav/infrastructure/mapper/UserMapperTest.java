package com.project.trav.infrastructure.mapper;

import com.project.trav.model.entity.Role;
import com.project.trav.model.entity.Status;
import com.project.trav.model.entity.User;
import com.project.trav.model.dto.UserDto;
import com.project.trav.mapper.CityMapperImpl;
import com.project.trav.mapper.RaceMapperImpl;
import com.project.trav.mapper.TicketMapperImpl;
import com.project.trav.mapper.UserMapper;
import com.project.trav.mapper.UserMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserMapperImpl.class, TicketMapperImpl.class, RaceMapperImpl.class, CityMapperImpl.class})
public class UserMapperTest {
    @Autowired
    private UserMapper mapper;
    @Test
    void toUser(){
    var sourceUserDto = new UserDto()
            .setId(1L)
            .setName("Ivan")
            .setSurname("Baranetskyi")
            .setMail("baranetskyi@gmail.com")
            .setPhone("+380956954604")
            .setLogin("kreker")
            .setPassword("admin")
            .setRole(Role.ADMIN)
            .setStatus(Status.ACTIVE)
            .setTicketsDto(null);
    var resultUser = mapper.toUser(sourceUserDto);
    var expectedUser = new User()
            .setId(1L)
            .setName("Ivan")
            .setSurname("Baranetskyi")
            .setMail("baranetskyi@gmail.com")
            .setPhone("+380956954604")
            .setLogin("kreker")
            .setPassword("admin")
            .setRole(Role.ADMIN)
            .setStatus(Status.ACTIVE)
            .setTickets(null);
        assertThat(resultUser).isEqualTo(expectedUser);
    }
    @Test
    void toUserDto(){
        var sourceUser = new User()
                .setId(1L)
                .setName("Ivan")
                .setSurname("Baranetskyi")
                .setMail("baranetskyi@gmail.com")
                .setPhone("+380956954604")
                .setLogin("kreker")
                .setPassword("admin")
                .setRole(Role.ADMIN)
                .setStatus(Status.ACTIVE)
                .setTickets(null);
        var resultUserDto = mapper.toUserDto(sourceUser);
        var expectedUserDto = new UserDto()
                .setId(1L)
                .setName("Ivan")
                .setSurname("Baranetskyi")
                .setMail("baranetskyi@gmail.com")
                .setPhone("+380956954604")
                .setLogin("kreker")
                .setPassword("admin")
                .setRole(Role.ADMIN)
                .setStatus(Status.ACTIVE)
                .setTicketsDto(null);
        assertThat(resultUserDto).isEqualTo(expectedUserDto);
    }
    @Test
    void toUserDtos(){
        var sourceUserList = Arrays.asList( new User()
                .setId(1L)
                .setName("Ivan")
                .setSurname("Baranetskyi")
                .setMail("baranetskyi@gmail.com")
                .setPhone("+380956954604")
                .setLogin("kreker")
                .setPassword("admin")
                .setRole(Role.ADMIN)
                .setStatus(Status.ACTIVE)
                .setTickets(null),new User()
                .setId(1L)
                .setName("Ivan")
                .setSurname("Baranetskyi")
                .setMail("baranetskyi@gmail.com")
                .setPhone("+380956954604")
                .setLogin("kreker")
                .setPassword("admin")
                .setRole(Role.ADMIN)
                .setStatus(Status.ACTIVE)
                .setTickets(null));
        var resultUserDtos = mapper.toUserDtos(sourceUserList);
        var expectedUserDtos =Arrays.asList( new UserDto()
                .setId(1L)
                .setName("Ivan")
                .setSurname("Baranetskyi")
                .setMail("baranetskyi@gmail.com")
                .setPhone("+380956954604")
                .setLogin("kreker")
                .setPassword("admin")
                .setRole(Role.ADMIN)
                .setStatus(Status.ACTIVE)
                .setTicketsDto(null),new UserDto()
                .setId(1L)
                .setName("Ivan")
                .setSurname("Baranetskyi")
                .setMail("baranetskyi@gmail.com")
                .setPhone("+380956954604")
                .setLogin("kreker")
                .setPassword("admin")
                .setRole(Role.ADMIN)
                .setStatus(Status.ACTIVE)
                .setTicketsDto(null));
        assertThat(resultUserDtos).isEqualTo(expectedUserDtos);

    }
}

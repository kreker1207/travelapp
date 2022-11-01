package com.project.trav.infrastructure.mapper;

import com.project.trav.model.entity.Race;
import com.project.trav.model.entity.Ticket;
import com.project.trav.model.entity.TicketStatus;
import com.project.trav.model.dto.RaceDto;
import com.project.trav.model.dto.TicketDto;
import com.project.trav.mapper.CityMapperImpl;
import com.project.trav.mapper.RaceMapperImpl;
import com.project.trav.mapper.TicketMapper;
import com.project.trav.mapper.TicketMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TicketMapperImpl.class, RaceMapperImpl.class, CityMapperImpl.class})
public class TicketMapperTest {
    @Autowired private TicketMapper mapper;

    @Test
    void toTicket(){
        var raceDto = new RaceDto()
                .setId(1L)
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityIdDto(null)
                .setArrivalCityIdDto(null);
        var race = new Race()
                .setId(1L)
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityId(null)
                .setArrivalCityId(null);
        var sourceTicketDto = new TicketDto()
                .setId(1L)
                .setUserId(1L)
                .setPlace("A21")
                .setPlaceClass("economy")
                .setCost("200")
                .setTicketStatus(TicketStatus.AVAILABLE)
                .setRacesDto(raceDto);
        var resultTicket = mapper.toTicket(sourceTicketDto);
        var ticketExpected =  new Ticket()
                .setId(1L)
                .setUserId(1L)
                .setPlace("A21")
                .setPlaceClass("economy")
                .setCost("200")
                .setTicketStatus(TicketStatus.AVAILABLE)
                .setRaces(race);
        assertThat(resultTicket).isEqualTo(ticketExpected);
    }
    @Test
    void toTicketDto(){
        var raceDto = new RaceDto()
                .setId(1L)
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityIdDto(null)
                .setArrivalCityIdDto(null);
        var race = new Race()
                .setId(1L)
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityId(null)
                .setArrivalCityId(null);
        var sourceTicket = new Ticket()
                .setId(1L)
                .setUserId(1L)
                .setPlace("A21")
                .setPlaceClass("economy")
                .setCost("200")
                .setTicketStatus(TicketStatus.AVAILABLE)
                .setRaces(race);
        var resultTicketDto = mapper.toTicketDto(sourceTicket);
        var ticketDtoExpected =  new TicketDto()
                .setId(1L)
                .setUserId(1L)
                .setPlace("A21")
                .setPlaceClass("economy")
                .setCost("200")
                .setTicketStatus(TicketStatus.AVAILABLE)
                .setRacesDto(raceDto);
        assertThat(resultTicketDto).isEqualTo(ticketDtoExpected);
    }
    @Test
    void toTicketDtos(){
        var raceDto = new RaceDto()
                .setId(1L)
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityIdDto(null)
                .setArrivalCityIdDto(null);
        var race = new Race()
                .setId(1L)
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityId(null)
                .setArrivalCityId(null);
        var sourceTicketList = Arrays.asList(new Ticket()
                .setId(1L)
                .setUserId(1L)
                .setPlace("A21")
                .setPlaceClass("economy")
                .setCost("200")
                .setTicketStatus(TicketStatus.AVAILABLE)
                .setRaces(race), new  Ticket()
                .setUserId(1L)
                .setPlace("A21")
                .setPlaceClass("economy")
                .setCost("200")
                .setTicketStatus(TicketStatus.AVAILABLE)
                .setRaces(race));
        var resultTicketDto = mapper.toTicketDtos(sourceTicketList);
        var expectedTicketList = Arrays.asList(new TicketDto()
                .setId(1L)
                .setUserId(1L)
                .setPlace("A21")
                .setPlaceClass("economy")
                .setCost("200")
                .setTicketStatus(TicketStatus.AVAILABLE)
                .setRacesDto(raceDto), new  TicketDto()
                .setUserId(1L)
                .setPlace("A21")
                .setPlaceClass("economy")
                .setCost("200")
                .setTicketStatus(TicketStatus.AVAILABLE)
                .setRacesDto(raceDto));
        assertThat(resultTicketDto).isEqualTo(expectedTicketList);
    }
}

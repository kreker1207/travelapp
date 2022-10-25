package com.project.trav.infrastructure.mapper;

import com.project.trav.domain.entity.Race;
import com.project.trav.ifrastructure.dto.RaceDto;
import com.project.trav.ifrastructure.mapper.CityMapperImpl;
import com.project.trav.ifrastructure.mapper.RaceMapper;
import com.project.trav.ifrastructure.mapper.RaceMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RaceMapperImpl.class, CityMapperImpl.class})
public class RaceMapperTest {
    @Autowired private RaceMapper mapper;
    @Test
    void toRace(){
        var sourceRaceDto = new RaceDto()
                .setId(1L)
                .setDepartureCity("Kiev")
                .setArrivalCity("Berlin")
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityIdDto(null)
                .setArrivalCityIdDto(null);
        var resultRace = mapper.toRace(sourceRaceDto);
        var raceExpected =  new Race()
                .setId(1L)
                .setDepartureCity("Kiev")
                .setArrivalCity("Berlin")
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityId(null)
                .setArrivalCityId(null);
        assertThat(resultRace).isEqualTo(raceExpected);
    }
    @Test
    void toRaceDtos(){
        var sourceRaceList = Arrays.asList(new  Race()
                .setId(1L)
                .setDepartureCity("Kiev")
                .setArrivalCity("Berlin")
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityId(null)
                .setArrivalCityId(null),new  Race()
                .setId(1L)
                .setDepartureCity("Kiev")
                .setArrivalCity("Berlin")
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityId(null)
                .setArrivalCityId(null));
        var resultRacesDto = mapper.toRaceDtos(sourceRaceList);
        var expectedRaceList = Arrays.asList(new  RaceDto()
                .setId(1L)
                .setDepartureCity("Kiev")
                .setArrivalCity("Berlin")
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityIdDto(null)
                .setArrivalCityIdDto(null),new  RaceDto()
                .setId(1L)
                .setDepartureCity("Kiev")
                .setArrivalCity("Berlin")
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityIdDto(null)
                .setArrivalCityIdDto(null));
        assertThat(resultRacesDto).isEqualTo(expectedRaceList);
    }
    @Test
    void toRaceDto(){
        var sourceRace = new Race()
                .setId(1L)
                .setDepartureCity("Kiev")
                .setArrivalCity("Berlin")
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityId(null)
                .setArrivalCityId(null);
        var resultRaceDto = mapper.toRaceDto(sourceRace);
        var raceDtoExpected =  new RaceDto()
                .setId(1L)
                .setDepartureCity("Kiev")
                .setArrivalCity("Berlin")
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityIdDto(null)
                .setArrivalCityIdDto(null);
        assertThat(resultRaceDto).isEqualTo(raceDtoExpected);
    }

}

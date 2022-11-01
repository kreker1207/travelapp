package com.project.trav.infrastructure.mapper;

import com.project.trav.model.dto.CityDto;
import com.project.trav.model.entity.City;
import com.project.trav.model.entity.Race;
import com.project.trav.model.dto.RaceDto;
import com.project.trav.mapper.CityMapperImpl;
import com.project.trav.mapper.RaceMapper;
import com.project.trav.mapper.RaceMapperImpl;
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
    City city = new City().setName("Kiev").setCountry("Ukraine").setPopulation("3.3 million")
        .setInformation("capital");
    CityDto cityDto = new CityDto().setName("Kiev").setCountry("Ukraine").setPopulation("3.3 million")
        .setInformation("capital");
    @Test
    void toRace(){
        var sourceRaceDto = new RaceDto()
                .setId(1L)
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityIdDto(cityDto)
                .setArrivalCityIdDto(cityDto);
        var resultRace = mapper.toRace(sourceRaceDto);
        var raceExpected =  new Race()
                .setId(1L)
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityId(city)
                .setArrivalCityId(city);
        assertThat(resultRace).isEqualTo(raceExpected);
    }
    @Test
    void toRaceDtos(){
        var sourceRaceList = Arrays.asList(new  Race()
                .setId(1L)
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityId(city)
                .setArrivalCityId(city),new  Race()
                .setId(1L)
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityId(city)
                .setArrivalCityId(city));
        var resultRacesDto = mapper.toRaceDtos(sourceRaceList);
        var expectedRaceList = Arrays.asList(new  RaceDto()
                .setId(1L)
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityIdDto(cityDto)
                .setArrivalCityIdDto(cityDto),new  RaceDto()
                .setId(1L)
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityIdDto(cityDto)
                .setArrivalCityIdDto(cityDto));
        assertThat(resultRacesDto).isEqualTo(expectedRaceList);
    }
    @Test
    void toRaceDto(){
        var sourceRace = new Race()
                .setId(1L)
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityId(city)
                .setArrivalCityId(city);
        var resultRaceDto = mapper.toRaceDto(sourceRace);
        var raceDtoExpected =  new RaceDto()
                .setId(1L)
                .setDepartureTime("12-00")
                .setArrivalTime("15-00")
                .setTravelTime("3")
                .setAirline("Mau")
                .setRaceNumber("Wz-air-222")
                .setDepartureCityIdDto(cityDto)
                .setArrivalCityIdDto(cityDto);
        assertThat(resultRaceDto).isEqualTo(raceDtoExpected);
    }

}

package com.project.trav.application.service;

import com.project.trav.application.services.RaceService;
import com.project.trav.domain.entity.City;
import com.project.trav.domain.entity.Race;
import com.project.trav.domain.repository.RaceRepository;
import com.project.trav.exeption.EntityAlreadyExists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class RaceServiceTest {
    @Mock
    private RaceRepository raceRepository;
    @InjectMocks
    private RaceService raceService;
    @Captor
    private ArgumentCaptor<Race> raceArgumentCaptor;
    City city = new City().setId(1L).setName("Kiev").setCountry("Ukraine").setPopulation("2.7 million").setInformation("Capital");
    @Test
    void getRaces(){
       var raceList  = Arrays.asList( new Race().setId(1L).setDepartureCity("Kiev").setArrivalCity("Berlin")
                       .setDepartureTime(LocalTime.parse("12:00")).setArrivalTime(LocalTime.parse("15:00"))
               .setTravelTime(LocalTime.parse("03:00")).setAirline("Mau").setRaceNumber("Wz-air-222").setDepartureCityId(city).setArrivalCityId(city),
               new Race().setId(1L).setDepartureCity("Kiev").setArrivalCity("Berlin")
                       .setDepartureTime(LocalTime.parse("12:00")).setArrivalTime(LocalTime.parse("15:00"))
                       .setTravelTime(LocalTime.parse("03:00")).setAirline("Mau").setRaceNumber("Wz-air-222")
                       .setDepartureCityId(city).setArrivalCityId(city)
        );
        Mockito.when(raceRepository.findAll()).thenReturn(raceList);
        var expectedList = raceService.getRaces();
        assertThat(expectedList).isEqualTo(raceList);
    }
    @Test
    void getRace_success(){
        var sourceRace = new Race().setId(1L).setDepartureCity("Kiev").setArrivalCity("Berlin")
                .setDepartureTime(LocalTime.parse("12:00")).setArrivalTime(LocalTime.parse("15:00"))
                .setTravelTime(LocalTime.parse("03:00")).setAirline("Mau").setRaceNumber("Wz-air-222")
                .setDepartureCityId(city).setArrivalCityId(city);
        Mockito.when(raceRepository.findById(1L)).thenReturn(Optional.of(sourceRace));
        var expectedRace = raceService.getRace(1L);
        assertThat(sourceRace).isEqualTo(expectedRace);
    }
    @Test
    void getRace_failure(){
        Mockito.when(raceRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->raceService.getRace(1L));
    }
    @Test
    void deleteRace_success(){
        Mockito.when(raceRepository.existsById(1L)).thenReturn(true);
        raceService.deleteRace(1L);
        Mockito.verify(raceRepository).deleteById(1L);
    }
    @Test
    void deleteRace_failure(){
        Mockito.when(raceRepository.existsById(1L)).thenReturn(false);
        String expectedMessage = "Race was not found by id";
        String actualMessage = Assertions.assertThrows(EntityNotFoundException.class,()->
                raceService.deleteRace(1L)).getMessage();
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }
    @Test
    void addRace_success(){
        var race = new Race().setId(1L).setDepartureCity("Kiev").setArrivalCity("Berlin")
                .setDepartureTime(LocalTime.parse("12:00")).setArrivalTime(LocalTime.parse("15:00"))
                .setTravelTime(LocalTime.parse("03:00")).setAirline("Mau").setRaceNumber("Wz-air-222")
                .setDepartureCityId(city).setArrivalCityId(city);
        Mockito.when(raceRepository.findByRaceNumber("Wz-air-222").isEmpty()).thenReturn(null);
        raceService.addRace(race);
        Mockito.verify(raceRepository).save(race);
    }
    @Test
    void addRace_failure(){
        var race = new Race().setId(1L).setDepartureCity("Kiev").setArrivalCity("Berlin")
                .setDepartureTime(LocalTime.parse("12:00")).setArrivalTime(LocalTime.parse("15:00"))
                .setTravelTime(LocalTime.parse("03:00")).setAirline("Mau").setRaceNumber("Wz-air-222")
                .setDepartureCityId(city).setArrivalCityId(city);
        Mockito.when(raceRepository.findByRaceNumber("Wz-air-222")).thenReturn(Optional.ofNullable(race));
        String expectedMessage = "Race with this Number already exists";
        String actualMessage = Assertions.assertThrows(EntityAlreadyExists.class,()->
                raceService.addRace(race)).getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
    @Test
    void updateRace_success(){
        var sourceRace =new Race().setId(1L).setDepartureCity("Kiev").setArrivalCity("Berlin")
                .setDepartureTime(LocalTime.parse("12:00")).setArrivalTime(LocalTime.parse("15:00"))
                .setTravelTime(LocalTime.parse("03:00")).setAirline("Mau").setRaceNumber("Wz-air-222")
                .setDepartureCityId(city).setArrivalCityId(city);
        var expectedRace = new Race().setId(1L).setDepartureCity("Kiev").setArrivalCity("Berlin")
                .setDepartureTime(LocalTime.parse("12:00")).setArrivalTime(LocalTime.parse("15:00"))
                .setTravelTime(LocalTime.parse("03:00")).setAirline("Mau").setRaceNumber("Wz-air-222")
                .setDepartureCityId(city).setArrivalCityId(city);

        Mockito.when(raceRepository.existsById(1L)).thenReturn(true);
        Mockito.when(raceRepository.findByRaceNumber("Wz-air-222").isEmpty()).thenReturn(null);
        raceService.updateRace(sourceRace,1L);
        Mockito.verify(raceRepository).save(raceArgumentCaptor.capture());
        assertThat(raceArgumentCaptor.getValue()).isEqualTo(expectedRace);
    }
    @Test
    void updateRace_FoundById_failure(){
        var race = new Race().setId(1L).setDepartureCity("Kiev").setArrivalCity("Berlin")
                .setDepartureTime(LocalTime.parse("12:00")).setArrivalTime(LocalTime.parse("15:00"))
                .setTravelTime(LocalTime.parse("03:00")).setAirline("Mau").setRaceNumber("Wz-air-222")
                .setDepartureCityId(city).setArrivalCityId(city);
        Mockito.when(raceRepository.existsById(1L)).thenReturn(false);
        String expectedMessage = "Race was not found by id";
        String actualMessage = Assertions.assertThrows(EntityNotFoundException.class,()->
                raceService.updateRace(race,1L)).getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
    @Test
    void updateRace_FoundByNumber_failure(){
        var race = new Race().setId(1L).setDepartureCity("Kiev").setArrivalCity("Berlin")
                .setDepartureTime(LocalTime.parse("12:00")).setArrivalTime(LocalTime.parse("15:00"))
                .setTravelTime(LocalTime.parse("03:00")).setAirline("Mau").setRaceNumber("Wz-air-222")
                .setDepartureCityId(city).setArrivalCityId(city);
        Mockito.when(raceRepository.findByRaceNumber("Wz-air-222")).thenReturn(Optional.ofNullable(race));
        String expectedMessage = "Race with this Number already exists";
        String actualMessage = Assertions.assertThrows(EntityAlreadyExists.class,()->
                raceService.addRace(race)).getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}

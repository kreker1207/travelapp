package com.project.trav.application.service;

import com.project.trav.application.services.RaceService;
import com.project.trav.domain.entity.City;
import com.project.trav.domain.entity.Race;
import com.project.trav.domain.repository.RaceRepository;
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
import java.util.Arrays;
import java.util.List;
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
        List<Race> raceList  = Arrays.asList(
                new Race().setId(1L).setDepartureTime("12-00").setArrivalTime("13-00").setDepartureCity("Kiev").setArrivalCity("Berlin")
                        .setTravelTime("1").setAirline("Mau").setRaceNumber("Wr23-ww").setDepartureCityId(city).setArrivalCityId(city),
                new Race().setId(1L).setDepartureTime("12-00").setArrivalTime("13-00").setDepartureCity("Kiev").setArrivalCity("Berlin")
                        .setTravelTime("1").setAirline("Mau").setRaceNumber("Wr23-ww").setDepartureCityId(city).setArrivalCityId(city)
        );
        Mockito.when(raceRepository.findAll()).thenReturn(raceList);
        List<Race> expectedList = raceService.getRaces();
        assertThat(expectedList).isEqualTo(raceList);
    }
    @Test
    void getRace_success(){
        Race sourceRace = new Race().setId(1L).setDepartureTime("12-00").setArrivalTime("13-00").setDepartureCity("Kiev").setArrivalCity("Berlin")
                .setTravelTime("1").setAirline("Mau").setRaceNumber("Wr23-ww").setDepartureCityId(city).setArrivalCityId(city);
        Mockito.when(raceRepository.findById(1L)).thenReturn(Optional.of(sourceRace));
        Race expectedRace = raceService.getRace(1L);
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
    void addRace(){
        Race race = new Race().setId(1L).setDepartureTime("12-00").setArrivalTime("13-00").setDepartureCity("Kiev").setArrivalCity("Berlin")
                .setTravelTime("1").setAirline("Mau").setRaceNumber("Wr23-ww").setDepartureCityId(city).setArrivalCityId(city);
        raceService.addRace(race);
        Mockito.verify(raceRepository).save(race);
    }
    @Test
    void updateRace_success(){
        Race sourceRace =new Race().setId(1L).setDepartureTime("12-00").setArrivalTime("13-00").setDepartureCity("Kiev").setArrivalCity("Berlin")
                .setTravelTime("1").setAirline("Mau").setRaceNumber("Wr23-ww").setDepartureCityId(city).setArrivalCityId(city);
        Race expectedRace = new Race().setId(1L).setDepartureTime("12-00").setArrivalTime("13-00").setDepartureCity("Kiev").setArrivalCity("Berlin")
                .setTravelTime("1").setAirline("Mau").setRaceNumber("Wr23-ww").setDepartureCityId(city).setArrivalCityId(city);

        Mockito.when(raceRepository.existsById(1L)).thenReturn(true);

        raceService.updateRace(sourceRace,1L);
        Mockito.verify(raceRepository).save(raceArgumentCaptor.capture());
        assertThat(raceArgumentCaptor.getValue()).isEqualTo(expectedRace);
    }
    @Test
    void updateRace_failure(){
        Race race = new Race().setId(1L).setDepartureTime("12-00").setArrivalTime("13-00").setDepartureCity("Kiev").setArrivalCity("Berlin")
                .setTravelTime("1").setAirline("Mau").setRaceNumber("Wr23-ww").setDepartureCityId(city).setArrivalCityId(city);
        Mockito.when(raceRepository.existsById(1L)).thenReturn(false);
        String expectedMessage = "Race was not found by id";
        String actualMessage = Assertions.assertThrows(EntityNotFoundException.class,()->
                raceService.updateRace(race,1L)).getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}

package com.project.trav.application.service;

import com.project.trav.application.services.CityService;
import com.project.trav.domain.entity.City;
import com.project.trav.domain.repository.CityRepository;
import org.assertj.core.api.AssertionsForClassTypes;
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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {
    @Mock
    private CityRepository cityRepository;
    @InjectMocks
    private CityService cityService;
    @Captor
    private ArgumentCaptor<City> cityArgumentCaptor;
    @Test
    void getCities(){
        var cityList = Arrays.asList(new City().setId(1L).setName("Kiev").setCountry("Ukraine")
                        .setPopulation("3.2 million").setInformation("capital"),
                new City().setName("Kiev").setCountry("Ukraine").setPopulation("3.2 million").setInformation("capital"));
        Mockito.when(cityRepository.findAll()).thenReturn(cityList);
        var result = cityService.getCities();
        assertThat(cityList).isEqualTo(result);
    }
    @Test
    void getCity_success(){
        var sourceCity = new City().setId(1L).setName("Kiev").setCountry("Ukraine")
                .setPopulation("3.2 million").setInformation("capital");
        Mockito.when(cityRepository.findByName("Kiev")).thenReturn(sourceCity);
        var expectedCity = cityService.getCityInfo("Kiev");
        AssertionsForClassTypes.assertThat(sourceCity).isEqualTo(expectedCity);
    }
    @Test
    void getCity_failure(){
        Mockito.when(cityRepository.findByName("Kiev")).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class,()->cityService.getCityInfo("Kiev"));
    }
    @Test
    void add_city(){
        var city = new City().setId(1L).setName("Kiev").setCountry("Ukraine")
                .setPopulation("3.2 million").setInformation("capital");
        cityService.addCity(city);
        Mockito.verify(cityRepository).save(city);
    }
    @Test
    void deleteCity_success(){
        Mockito.when(cityRepository.existsById(1L)).thenReturn(true);
        cityService.deleteCity(1L);
        Mockito.verify(cityRepository).deleteById(1L);
    }
    @Test
    void deleteCity_failure(){
        Mockito.when(cityRepository.existsById(1L)).thenReturn(false);
        String expectedMessage = "City was not found";
        String actualMessage = Assertions.assertThrows(EntityNotFoundException.class,()->
                cityService.deleteCity(1L)).getMessage();
        assertThat(expectedMessage).isEqualTo(actualMessage);
    }
    @Test
    void updateCity_success(){
        var sourceCity =new City().setId(1L).setName("Kiev").setCountry("Ukraine")
                .setPopulation("3.2 million").setInformation("capital");
        var expectedCity = new City().setId(1L).setName("Kiev").setCountry("Ukraine")
                .setPopulation("3.2 million").setInformation("capital");

        Mockito.when(cityRepository.existsById(1L)).thenReturn(true);

        cityService.updateCity(sourceCity,1L);
        Mockito.verify(cityRepository).save(cityArgumentCaptor.capture());
        AssertionsForClassTypes.assertThat(cityArgumentCaptor.getValue()).isEqualTo(expectedCity);
    }
    @Test
    void updateRace_failure(){
        var city = new City().setId(1L).setName("Kiev").setCountry("Ukraine")
                .setPopulation("3.2 million").setInformation("capital");
        Mockito.when(cityRepository.existsById(1L)).thenReturn(false);
        String expectedMessage = "City was not found";
        String actualMessage = Assertions.assertThrows(EntityNotFoundException.class,()->
                cityService.updateCity(city,1L)).getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}

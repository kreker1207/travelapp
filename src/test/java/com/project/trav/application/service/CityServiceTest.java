package com.project.trav.application.service;

import com.project.trav.mapper.CityMapper;
import com.project.trav.model.dto.CityDto;
import com.project.trav.service.CityService;
import com.project.trav.model.entity.City;
import com.project.trav.repository.CityRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

  @Mock
  private CityMapper cityMapper;
  @Mock
  private CityRepository cityRepository;
  @InjectMocks
  private CityService cityService;

  @Test
  void getCities() {
    var cityList = Arrays.asList(new CityDto().setId(1L).setName("Kiev").setCountry("Ukraine")
            .setPopulation("3.2 million").setInformation("capital"),
        new CityDto().setName("Kiev").setCountry("Ukraine").setPopulation("3.2 million")
            .setInformation("capital"));
    Mockito.when(cityMapper.toCityDtos(Mockito.anyList())).thenReturn(cityList);
    var result = cityService.getCities();
    assertThat(cityList).isEqualTo(result);
  }

  @Test
  void getCity_success() {
    var sourceCity = new City().setId(1L).setName("Kiev").setCountry("Ukraine")
        .setPopulation("3.2 million").setInformation("capital");
    Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(sourceCity));
    var expectedCity = cityService.getCity(1L);
    AssertionsForClassTypes.assertThat(cityMapper.toCityDto(sourceCity)).isEqualTo(expectedCity);
  }

  @Test
  void getCity_failure() {
    Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.empty());
    Assertions.assertThrows(EntityNotFoundException.class, () -> cityService.getCity(1L));
  }

  @Test
  void add_city() {
    var city = new CityDto().setId(1L).setName("Kiev").setCountry("Ukraine")
        .setPopulation("3.2 million").setInformation("capital");
    cityService.addCity(city);
    Mockito.verify(cityRepository).save(cityMapper.toCity(city));
  }

  @Test
  void deleteCity_success() {
    var city = new City().setId(1L).setName("Kiev").setCountry("Ukraine")
        .setPopulation("3.2 million").setInformation("capital");
    Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
    cityService.deleteCity(1L);
    Mockito.verify(cityRepository).deleteById(1L);
  }

  @Test
  void deleteCity_failure() {
    Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.empty());
    String expectedMessage = "City was not found";
    String actualMessage = Assertions.assertThrows(EntityNotFoundException.class, () ->
        cityService.deleteCity(1L)).getMessage();
    assertThat(expectedMessage).isEqualTo(actualMessage);
  }

  @Test
  void updateCity_success() {
    var sourceCity = new CityDto().setId(1L).setName("Kiev").setCountry("Ukraine")
        .setPopulation("3.2 million").setInformation("capital");
    var city = new City().setId(1L).setName("Kiev").setCountry("Ukraine")
        .setPopulation("3.2 million").setInformation("capital");
    Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
    Mockito.when(cityMapper.toCity(sourceCity)).thenReturn(city);
    cityService.updateCity(sourceCity, 1L);
    Mockito.verify(cityRepository).save(city);
  }

  @Test
  void updateRace_failure() {
    var city = new CityDto().setId(1L).setName("Kiev").setCountry("Ukraine")
        .setPopulation("3.2 million").setInformation("capital");
    Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.empty());
    String expectedMessage = "City was not found";
    String actualMessage = Assertions.assertThrows(EntityNotFoundException.class, () ->
        cityService.updateCity(city, 1L)).getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }
}

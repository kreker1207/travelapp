package com.project.trav.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.trav.TravelApplication;
import com.project.trav.exeption.EntityNotFoundByIdException;
import com.project.trav.model.dto.CityDto;
import com.project.trav.model.dto.CityDto.Fields;
import com.project.trav.repository.CityH2Repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = TravelApplication.class)
@ExtendWith(SpringExtension.class)
@Transactional
public class CityServiceIntegrationTest {

  @Autowired
  private CityService cityService;
  @Autowired
  private CityH2Repository cityH2Repository;


  @AfterEach
  public void clearDataBase() {
    cityH2Repository.deleteAll();
  }

  @Test
  void createCity_success() {
    var resultCityDto = cityService.addCity(createCityDto());
    assertEquals(1, cityH2Repository.findAll().size());
    var expectedCity = new CityDto()
        .setName("Kiev")
        .setCountry("Ukraine")
        .setPopulation("3.2m")
        .setInformation("Capital city");
    assertThat(resultCityDto).usingRecursiveComparison().ignoringFields(Fields.id)
        .isEqualTo(expectedCity);
  }

  @Test
  void getNotFound_fail() {
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      cityService.getCity(1L);
    }, "City was not found");
    assertEquals("City was not found", thrown.getMessage());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id, name, country, population, info) "
      + "VALUES (1,'Kiev','Ukraine','3.2m','Capital city')")
  void getCity_success() {
    assertEquals(1, cityH2Repository.findAll().size());
    var expectedCity = new CityDto()
        .setName("Kiev")
        .setCountry("Ukraine")
        .setPopulation("3.2m")
        .setInformation("Capital city");
    var resultCityDto = cityService.getCity(1L);
    assertThat(resultCityDto).usingRecursiveComparison().ignoringFields(Fields.id)
        .isEqualTo(expectedCity);
  }

  @Test
  @Sql(statements = "INSERT INTO city (id, name, country, population, info) "
      + "VALUES (1,'Kiev','Ukraine','3.2m','Capital city')")
  @Sql(statements = "INSERT INTO city (id, name, country, population, info) "
      + "VALUES (2,'Berlin','Germany','3.5m','Capital city')")
  void getCities_success() {
    var foundCities = cityService.getCities();
    assertEquals(2, cityH2Repository.findAll().size());
    assertEquals(2, foundCities.size());
    assertEquals("Kiev", foundCities.get(0).getName());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id, name, country, population, info) "
      + "VALUES (1,'Kiev','Ukraine','3.2m','Capital city')")
  @Sql(statements = "INSERT INTO city (id, name, country, population, info) "
      + "VALUES (2,'Berlin','Germany','3.5m','Capital city')")
  void deleteCities_success() {
    assertEquals(2, cityService.getCities().size());
    cityService.deleteCity(1L);
    assertEquals(1, cityService.getCities().size());
  }

  @Test
  void deleteNotFound_fail() {
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      cityService.deleteCity(1L);
    }, "City was not found");
    assertEquals("City was not found", thrown.getMessage());
  }

  @Test
  @Sql(statements = "INSERT INTO city (id, name, country, population, info) "
      + "VALUES (1,'Kiev','Ukraine','3.2m','Capital city')")
  @Sql(statements = "INSERT INTO city (id, name, country, population, info) "
      + "VALUES (2,'Berlin','Germany','3.5m','Capital city')")
  void updateCities_success() {
    assertEquals("3.2m", cityService.getCity(1L).getPopulation());
    var expectedCity = new CityDto().setName("Kiev").setCountry("Ukraine").setPopulation("3.2m")
        .setInformation("Capital city");
    var resultCityDto = cityService.updateCity(createCityDto(), 1L);
    assertThat(resultCityDto).usingRecursiveComparison().ignoringFields(Fields.id)
        .isEqualTo(expectedCity);
  }

  @Test
  void updateNotFound_fail() {
    EntityNotFoundByIdException thrown = assertThrows(EntityNotFoundByIdException.class, () -> {
      cityService.updateCity(createCityDto(), 1L);
    }, "City was not found");
    assertEquals("City was not found", thrown.getMessage());
  }

  private CityDto createCityDto() {
    return new CityDto()
        .setId(1L)
        .setName("Kiev")
        .setCountry("Ukraine")
        .setPopulation("3.2m")
        .setInformation("Capital city");
  }
}

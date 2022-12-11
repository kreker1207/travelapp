package com.project.trav.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.project.trav.TravelApplication;
import com.project.trav.mapper.CityMapper;
import com.project.trav.model.dto.CityDto;
import com.project.trav.model.entity.City;
import com.project.trav.repository.CityH2Repository;
import java.util.List;
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
  private CityMapper cityMapper;
  @Autowired
  private CityH2Repository cityH2Repository;



  @AfterEach
  public void clearDataBase() {
    cityH2Repository.deleteAll();
  }

  @Test
  void createCity() {
    CityDto city = new CityDto().setName("Kakhovka").setCountry("Ukraine").setPopulation("75k")
        .setInformation("small town");
    cityService.addCity(city);
    assertEquals(1, cityH2Repository.findAll().size());
    List<CityDto> foundCity = cityService.getCities();
    assertEquals("Kakhovka", foundCity.get(0).getName());
  }

  @Test
  void getCity() {
  City city = createAndSaveCity();
    assertEquals(1,cityH2Repository.findAll().size());
    CityDto foundCity = cityService.getCity(1L);
    assertEquals(cityMapper.toCityDto(city),foundCity);
  }
  @Test
  @Sql(statements = "INSERT INTO city (id, name, country, population, info) "
      + "VALUES (1,'Kiev','Ukraine','3.2m','Capital city')")
  @Sql(statements = "INSERT INTO city (id, name, country, population, info) "
      + "VALUES (2,'Berlin','Germany','3.5m','Capital city')")
  void getCities(){
    List<CityDto> foundCities = cityService.getCities();
    assertEquals(2,cityH2Repository.findAll().size());
    assertEquals(2,foundCities.size());
    assertEquals("Kiev",foundCities.get(0).getName());
  }
  @Test
  @Sql(statements = "INSERT INTO city (id, name, country, population, info) "
      + "VALUES (1,'Kiev','Ukraine','3.2m','Capital city')")
  @Sql(statements = "INSERT INTO city (id, name, country, population, info) "
      + "VALUES (2,'Berlin','Germany','3.5m','Capital city')")
  void deleteCities(){
    assertEquals(2,cityService.getCities().size());
    cityService.deleteCity(1L);
    assertEquals(1,cityService.getCities().size());
  }
  @Test
  @Sql(statements = "INSERT INTO city (id, name, country, population, info) "
      + "VALUES (1,'Kiev','Ukraine','3.2m','Capital city')")
  @Sql(statements = "INSERT INTO city (id, name, country, population, info) "
      + "VALUES (2,'Berlin','Germany','3.5m','Capital city')")
  void updateCities(){
    assertEquals("3.2m",cityService.getCity(1L).getPopulation());
    CityDto cityUpdate = new CityDto().setName("Kiev").setCountry("Ukraine").setPopulation("3.5m").setInformation("Capital city");
    cityService.updateCity(cityUpdate,1L);
    assertEquals("3.5m",cityService.getCity(1L).getPopulation());
  }

  private City createAndSaveCity() {
    return cityH2Repository.save(new City()
        .setId(1L)
        .setName("Kiev")
        .setCountry("Ukraine")
        .setPopulation("3.2m")
        .setInformation("Capital city"));
  }
}

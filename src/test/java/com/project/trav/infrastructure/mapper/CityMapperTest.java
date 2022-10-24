package com.project.trav.infrastructure.mapper;

import com.project.trav.domain.entity.City;
import com.project.trav.ifrastructure.dto.CityDto;
import com.project.trav.ifrastructure.mapper.CityMapper;
import com.project.trav.ifrastructure.mapper.CityMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CityMapperImpl.class})
public class CityMapperTest {
    @Autowired
    private CityMapper mapper;
    @Test
    void toCity(){
    var sourceCityDto = new CityDto().setId(1L).setName("Kiev").setCountry("Ukraine")
                .setPopulation("3.2 million").setInformation("capital");
    var result = mapper.toCity(sourceCityDto);
    var expectedCity = new City().setId(1L).setName("Kiev").setCountry("Ukraine")
            .setPopulation("3.2 million").setInformation("capital");
    assertThat(result).isEqualTo(expectedCity);
    }
    @Test
    void toCityDto(){
        var sourceCity = new City().setId(1L).setName("Kiev").setCountry("Ukraine")
                .setPopulation("3.2 million").setInformation("capital");
        var result = mapper.toCityDto(sourceCity);
        var expectedCity = new CityDto().setId(1L).setName("Kiev").setCountry("Ukraine")
                .setPopulation("3.2 million").setInformation("capital");
        assertThat(result).isEqualTo(expectedCity);
    }
    @Test
    void toCityDtos(){
    var sourceCityLists = Arrays.asList(
            new City().setId(1L).setName("Kiev").setCountry("Ukraine")
            .setPopulation("3.2 million").setInformation("capital"),
            new City().setId(1L).setName("Kiev").setCountry("Ukraine")
            .setPopulation("3.2 million").setInformation("capital"));
    var resultCityDto = mapper.toCityDtos(sourceCityLists);
    var expectedCityLists = Arrays.asList(
            new CityDto().setId(1L).setName("Kiev").setCountry("Ukraine")
                        .setPopulation("3.2 million").setInformation("capital"),
            new CityDto().setId(1L).setName("Kiev").setCountry("Ukraine")
                        .setPopulation("3.2 million").setInformation("capital"));
    assertThat(resultCityDto).isEqualTo(expectedCityLists);
    }
}

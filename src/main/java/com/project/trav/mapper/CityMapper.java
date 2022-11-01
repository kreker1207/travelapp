package com.project.trav.mapper;

import com.project.trav.model.entity.City;
import com.project.trav.model.dto.CityDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityDto toCityDto(City city);
    List<CityDto> toCityDtos(List<City> cities);
    City toCity(CityDto cityDto);
}

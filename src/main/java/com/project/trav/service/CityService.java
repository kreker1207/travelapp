package com.project.trav.service;

import com.project.trav.mapper.CityMapper;
import com.project.trav.model.dto.CityDto;
import com.project.trav.model.entity.City;
import com.project.trav.repository.CityRepository;
import com.project.trav.exeption.EntityNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

  private final CityMapper cityMapper;
  private static final String NOT_FOUND_ERROR = "City was not found";
  private final CityRepository cityRepository;

  public List<CityDto> getCities() {
    return cityMapper.toCityDtos(cityRepository.findAll());
  }

  public CityDto getCity(Long id) {
    return cityMapper.toCityDto(findById(id));
  }

  public CityDto addCity(CityDto cityDto) {
    cityRepository.save(cityMapper.toCity(cityDto));
    return cityDto;
  }

  public CityDto deleteCity(Long id) {
    City city = findById(id);
    cityRepository.deleteById(id);
    return cityMapper.toCityDto(city);
  }

  public CityDto updateCity(CityDto cityDto, Long id) {
    City oldCity = findById(id);
    City city = cityMapper.toCity(cityDto);
    cityRepository.save(oldCity.setName(city.getName()).setCountry(city.getCountry()).setPopulation(
        city.getPopulation()).setInformation(city.getInformation()));
    return cityDto;
  }

  private City findById(Long id) {
    return cityRepository.findById(id).orElseThrow(() -> {
      throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
    });
  }
}

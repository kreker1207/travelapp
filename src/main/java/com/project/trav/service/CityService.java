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

  public CityDto getCityInfo(String name) {
    return cityMapper.toCityDto(findByNameCity(name));
  }

  public void addCity(CityDto cityDto) {
    cityRepository.save(cityMapper.toCity(cityDto));
  }

  public void deleteCity(Long id) {
    existByIdCity(id);
    cityRepository.deleteById(id);
  }

  public void updateCity(CityDto cityDto, Long id) {
    existByIdCity(id);
    cityRepository.save(cityMapper.toCity(cityDto));
  }

  private City findByNameCity(String name) {
    return cityRepository.findByName(name).orElseThrow(() -> {
      throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
    });
  }

  private void existByIdCity(Long id) {
    if (!cityRepository.existsById(id)) {
      throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
    }
  }
}

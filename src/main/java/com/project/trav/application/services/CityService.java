package com.project.trav.application.services;

import com.project.trav.domain.entity.City;
import com.project.trav.domain.repository.CityRepository;
import com.project.trav.exeption.EntityNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CityService {
    private static final String NOT_FOUND_ERROR = "City was not found";
    private final CityRepository cityRepository;
    public List<City> getCities(){return cityRepository.findAll();}

    public City getCityInfo(String name) {
        City city = cityRepository.findByName(name);
        if (Objects.isNull(city)){
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        }
        else return city;
    }
    public void addCity(City city){ cityRepository.save(city);}
    public void deleteCity(Long id){
        if(!cityRepository.existsById(id)){
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        }
        cityRepository.deleteById(id);
    }
    public void updateCity(City city,Long id){
        if(!cityRepository.existsById(id)){
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        }
        cityRepository.save(city);
    }
}

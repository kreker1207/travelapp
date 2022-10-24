package com.project.trav.ifrastructure.rest;

import com.project.trav.application.services.CityService;
import com.project.trav.ifrastructure.dto.CityDto;
import com.project.trav.ifrastructure.mapper.CityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/city")
@RequiredArgsConstructor
public class CityController {
    private final CityMapper cityMapper;
    private final CityService cityService;

    @GetMapping("/{cityName}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('users','admins')")
    public CityDto getCityInfo(@PathVariable String cityName){return cityMapper.toCityDto(cityService.getCityInfo(cityName));}

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('users','admins')")
    public List<CityDto> getCities(){return cityMapper.toCityDtos(cityService.getCities());}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('admins')")
    public void addCity(@RequestBody CityDto cityDto){cityService.addCity(cityMapper.toCity(cityDto));}

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('admins')")
    public void deleteCity(@PathVariable Long id){cityService.deleteCity(id);}

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('admins')")
    public void updateCity(@RequestBody CityDto cityDto, @PathVariable Long id){
        cityService.updateCity(cityMapper.toCity(cityDto),id);}
}

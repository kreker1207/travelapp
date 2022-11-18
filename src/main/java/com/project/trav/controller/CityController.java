package com.project.trav.controller;

import static com.project.trav.configuration.SecurityConfiguration.SECURITY_CONFIG_NAME;

import com.project.trav.service.CityService;
import com.project.trav.model.dto.CityDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/city")
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
@RequiredArgsConstructor
public class CityController {

  private final CityService cityService;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public CityDto getCity(@PathVariable Long id) {
    return cityService.getCity(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public List<CityDto> getCities() {
    return cityService.getCities();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('admins')")
  public CityDto addCity(@Valid @RequestBody CityDto cityDto) {
    return cityService.addCity(cityDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAuthority('admins')")
  public CityDto deleteCity(@PathVariable Long id) {
    return cityService.deleteCity(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @PreAuthorize("hasAuthority('admins')")
  public CityDto updateCity(@Valid @RequestBody CityDto cityDto, @PathVariable Long id) {
    return cityService.updateCity(cityDto, id);
  }
}

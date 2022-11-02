package com.project.trav.controller;

import com.project.trav.service.RaceService;
import com.project.trav.model.dto.RaceDto;
import java.time.LocalTime;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/races")
@RequiredArgsConstructor
public class RaceController {

  private final RaceService raceService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public List<RaceDto> getRaces() {
    return raceService.getRaces();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public RaceDto getRace(@PathVariable Long id) {
    return raceService.getRace(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('admins')")
  public void addRace(@Valid @RequestBody RaceDto raceDto) {
    raceService.addRace(raceDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAuthority('admins')")
  public void deleteRace(@PathVariable Long id) {
    raceService.deleteRace(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @PreAuthorize("hasAuthority('admins')")
  public void updateRace(@Valid @RequestBody RaceDto raceDto, @PathVariable Long id) {
    raceService.updateRace(raceDto, id);
  }

  @GetMapping("/find")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("permitAll()")
  public List<RaceDto> searchByParams(
      @RequestParam(required = false) LocalTime departureTimeParam,
      @RequestParam(required = false) LocalTime arrivalTimeParam) {
    return raceService.searchByParams(departureTimeParam, arrivalTimeParam);

  }

}

package com.project.trav.controller;

import com.project.trav.model.dto.RaceUpdateRequest;
import com.project.trav.model.entity.Race;
import com.project.trav.repository.RaceRepository;
import com.project.trav.service.RaceService;
import com.project.trav.model.dto.RaceDto;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
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
@RequestMapping("/api/v1/races")
@RequiredArgsConstructor
public class RaceController {
  private final RaceService raceService;
  private final RaceRepository raceRepository;

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
  public RaceDto addRace(@Valid @RequestBody RaceDto raceDto) {
    return raceService.addRace(raceDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAuthority('admins')")
  public RaceDto deleteRace(@PathVariable Long id) {
     return raceService.deleteRace(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @PreAuthorize("hasAuthority('admins')")
  public RaceDto updateRace(@Valid @RequestBody RaceUpdateRequest raceUpdateRequest,
      @PathVariable Long id) {
    return raceService.updateRace(raceUpdateRequest, id);
  }
  @GetMapping("/search")
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public Page<Race> searchRace(@QuerydslPredicate(root = Race.class) Predicate predicate,
      Pageable pageable){
    return raceService.searchRaces(predicate,pageable);
  }

}

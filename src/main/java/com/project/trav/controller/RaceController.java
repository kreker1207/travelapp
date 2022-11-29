package com.project.trav.controller;

import static com.project.trav.configuration.SecurityConfiguration.SECURITY_CONFIG_NAME;

import com.project.trav.model.dto.RaceSaveRequest;
import com.project.trav.model.dto.RaceUpdateRequest;
import com.project.trav.service.RaceService;
import com.project.trav.model.dto.RaceDto;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class RaceController {
  private final RaceService raceService;
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get all races", responses = {
      @ApiResponse(responseCode = "200", description = "Races were found",
          content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RaceDto.class)))
          }),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public List<RaceDto> getRaces() {
    return raceService.getRaces();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get race by id", responses = {
      @ApiResponse(responseCode = "200", description = "Race was found by id",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = RaceDto.class))
          }),
      @ApiResponse(responseCode = "404", description = "Race was not found by id", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public RaceDto getRace(@PathVariable Long id) {
    return raceService.getRace(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create new race", responses = {
      @ApiResponse(responseCode = "201", description = "Race was created",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = RaceDto.class))
          }),
      @ApiResponse(responseCode = "400", description = "Bad request check fields", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAuthority('admins')")
  public RaceDto addRace(@Valid @RequestBody RaceSaveRequest raceSaveRequest) {
    return raceService.addRace(raceSaveRequest);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Delete race by id", responses = {
      @ApiResponse(responseCode = "200", description = "Race was deleted by id",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = RaceDto.class))
          }),
      @ApiResponse(responseCode = "404", description = "Race was not found by id", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAuthority('admins')")
  public RaceDto deleteRace(@PathVariable Long id) {
     return raceService.deleteRace(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @Operation(summary = "Update race", responses = {
      @ApiResponse(responseCode = "201", description = "Race was updated",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = RaceDto.class))
          }),
      @ApiResponse(responseCode = "400", description = "Bad request check fields", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content),
      @ApiResponse(responseCode = "404", description = "Race was not found by id", content = @Content)
  })
  @PreAuthorize("hasAuthority('admins')")
  public RaceDto updateRace(@Valid @RequestBody RaceUpdateRequest raceUpdateRequest,
      @PathVariable Long id) {
    return raceService.updateRace(raceUpdateRequest, id);
  }
  @GetMapping("/search")
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public Page<Race> searchRace(@QuerydslPredicate(root = Race.class,bindings = RaceRepository.class)
  Predicate predicate, Pageable pageable){
    return raceService.searchRaces(predicate,pageable);
  }
}

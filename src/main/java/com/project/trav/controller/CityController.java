package com.project.trav.controller;

import static com.project.trav.configuration.SecurityConfiguration.SECURITY_CONFIG_NAME;

import com.project.trav.service.CityService;
import com.project.trav.model.dto.CityDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
  @Operation(summary = "Get city by id", responses = {
      @ApiResponse(responseCode = "200", description = "City was found by id",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = CityDto.class))
          }),
      @ApiResponse(responseCode = "404", description = "City was not found by id", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public CityDto getCity(@PathVariable Long id) {
    return cityService.getCity(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)

  @Operation(summary = "Get all city", responses = {
      @ApiResponse(responseCode = "200", description = "Cities were found",
          content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CityDto.class)))
          }),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAnyAuthority('users','admins')")
  public List<CityDto> getCities() {
    return cityService.getCities();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create new city", responses = {
      @ApiResponse(responseCode = "201", description = "City was created",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = CityDto.class))
          }),
      @ApiResponse(responseCode = "400", description = "Bad request check fields", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAuthority('admins')")
  public CityDto addCity(@Valid @RequestBody CityDto cityDto) {
    return cityService.addCity(cityDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Delete city by id", responses = {
      @ApiResponse(responseCode = "200", description = "City was deleted by id",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = CityDto.class))
          }),
      @ApiResponse(responseCode = "404", description = "City was not found by id", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
  })
  @PreAuthorize("hasAuthority('admins')")
  public CityDto deleteCity(@PathVariable Long id) {
    return cityService.deleteCity(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @Operation(summary = "Update city", responses = {
      @ApiResponse(responseCode = "201", description = "City was updated",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = CityDto.class))
          }),
      @ApiResponse(responseCode = "400", description = "Bad request check fields", content = @Content),
      @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
      @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content),
      @ApiResponse(responseCode = "404", description = "City was not found by id", content = @Content)
  })
  @PreAuthorize("hasAuthority('admins')")
  public CityDto updateCity(@Valid @RequestBody CityDto cityDto, @PathVariable Long id) {
    return cityService.updateCity(cityDto, id);
  }
}

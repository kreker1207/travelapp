package com.project.trav.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CityDto {

  private Long id;
  @NotNull
  private String name;
  @NotNull
  private String country;
  @NotNull
  private String population;
  @NotNull
  private String information;
}

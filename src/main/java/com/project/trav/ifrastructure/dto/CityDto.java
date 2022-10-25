package com.project.trav.ifrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CityDto {
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String country;
    @NonNull
    private String population;
    @NonNull
    private String information;
}

package com.project.trav.ifrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FindParamDto {
    private String loginParam;
    private String mailParam;
    private String departureCityParam;
    private String arrivalCityParam;
    private String departureTimeParam;
    private String arrivalTimeParam;
}

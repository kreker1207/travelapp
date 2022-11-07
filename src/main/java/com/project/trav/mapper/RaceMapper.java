package com.project.trav.mapper;

import com.project.trav.model.entity.Race;
import com.project.trav.model.dto.RaceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CityMapperImpl.class)
public interface RaceMapper {

  @Mapping(target = "departureCityDto", source = "departureCity")
  @Mapping(target = "arrivalCityDto", source = "arrivalCity")
  RaceDto toRaceDto(Race race);

  List<RaceDto> toRaceDtos(List<Race> races);

  @Mapping(target = "departureCity", source = "departureCityDto")
  @Mapping(target = "arrivalCity", source = "arrivalCityDto")
  Race toRace(RaceDto raceDto);
}

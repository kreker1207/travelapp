package com.project.trav.mapper;

import com.project.trav.model.entity.Race;
import com.project.trav.model.dto.RaceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CityMapperImpl.class)
public interface RaceMapper {

  @Mapping(target = "departureCityIdDto", source = "departureCityId")
  @Mapping(target = "arrivalCityIdDto", source = "arrivalCityId")
  RaceDto toRaceDto(Race race);

  List<RaceDto> toRaceDtos(List<Race> races);

  @Mapping(target = "departureCityId", source = "departureCityIdDto")
  @Mapping(target = "arrivalCityId", source = "arrivalCityIdDto")
  Race toRace(RaceDto raceDto);
}

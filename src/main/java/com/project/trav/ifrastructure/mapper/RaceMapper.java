package com.project.trav.ifrastructure.mapper;

import com.project.trav.domain.entity.Race;
import com.project.trav.ifrastructure.dto.RaceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = CityMapperImpl.class)
public interface RaceMapper {
    @Mapping(target = "departureCityIdDto",source = "departureCityId")
    RaceDto toRaceDto(Race race);

    List<RaceDto> toRaceDtos(List<Race> races);
    @Mapping(target = "departureCityId",source = "departureCityIdDto")
    Race toRace(RaceDto raceDto);
}

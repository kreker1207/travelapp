package com.project.trav.ifrastructure.mapper;

import com.project.trav.domain.entity.Race;
import com.project.trav.ifrastructure.dto.RaceDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.FIELD)
public interface RaceMapper {
    RaceDto toRaceDto(Race race);

    List<RaceDto> toRaceDtos(List<Race> races);

    Race toRace(RaceDto raceDto);
}

package com.project.trav.service;

import com.project.trav.mapper.RaceMapper;
import com.project.trav.model.dto.RaceDto;
import com.project.trav.model.entity.Race;
import com.project.trav.repository.RaceRepository;
import com.project.trav.exeption.EntityNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RaceService {
    private static final String NOT_FOUND_ERROR = "Race was not found by id";
    private final RaceRepository raceRepository;
    private final RaceMapper raceMapper;

    public List<RaceDto> getRaces(){return raceMapper.toRaceDtos(raceRepository.findAll());}
    public RaceDto getRace(Long id) {
        return raceMapper.toRaceDto(findByIdRace(id));
    }
    public void addRace(RaceDto raceDto){ raceRepository.save(raceMapper.toRace(raceDto));}
    public void deleteRace(Long id){
        existByIdRace(id);
        raceRepository.deleteById(id);
    }
    public void updateRace(RaceDto raceDto,Long id){
        existByIdRace(id);
        raceRepository.save(raceMapper.toRace(raceDto));
    }
    public List<RaceDto> searchByParams(String departureTimeParam,String arrivalTimeParam){
        return raceMapper.toRaceDtos(raceRepository.DepartureTimeAndArrivalTime(departureTimeParam,arrivalTimeParam));
    }
    private Race findByIdRace(Long id) {
        return raceRepository.findById(id).orElseThrow(()->{
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        });

    }
    private void existByIdRace(Long id){
        if(!raceRepository.existsById(id)){
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        }
    }
}

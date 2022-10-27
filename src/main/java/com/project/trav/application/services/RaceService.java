package com.project.trav.application.services;

import com.project.trav.domain.entity.Race;
import com.project.trav.domain.entity.User;
import com.project.trav.domain.entity.UserRequest;
import com.project.trav.domain.repository.RaceRepository;
import com.project.trav.domain.repository.UserRepository;
import com.project.trav.exeption.EntityNotFoundByIdException;
import com.project.trav.ifrastructure.dto.FindParamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RaceService {
    private final UserRepository userRepository;
    private static final String NOT_FOUND_ERROR = "Race was not found by id";
    private final KafkaTemplate<String, UserRequest> kafkaTemplate;
    private final RaceRepository raceRepository;

    public List<Race> getRaces(){return raceRepository.findAll();}
    public Race getRace(Long id) {
        return raceRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        });
    }
    public void addRace(Race race){ raceRepository.save(race);}
    public void deleteRace(Long id){
        if(!raceRepository.existsById(id)){
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        }
        raceRepository.deleteById(id);
    }
    public void updateRace(Race race,Long id){
        if(!raceRepository.existsById(id)){
            throw new EntityNotFoundByIdException(NOT_FOUND_ERROR);
        }
        raceRepository.save(race);
    }
    public List<Race> searchByParams(String departureCityParam,String arrivalCityParam,String departureTimeParam,String arrivalTimeParam, String username){
        User user = userRepository.findByLogin(username).orElseThrow(()->
                new EntityNotFoundByIdException("User was not found"));
        FindParamDto findParamDto = new FindParamDto().setDepartureCityParam(departureCityParam).setArrivalCityParam(arrivalCityParam)
                .setDepartureTimeParam(departureTimeParam).setArrivalTimeParam(arrivalTimeParam);
        UserRequest userRequest = new UserRequest().setLogin(user.getLogin()).setMail(user.getMail()).setFindParamDto(findParamDto);
        kafkaTemplate.send("logsTopic",userRequest);
        return raceRepository.findByDepartureCityAndArrivalCityOrDepartureTimeAndArrivalTime(departureCityParam,arrivalCityParam,departureTimeParam,arrivalTimeParam);
    }
}

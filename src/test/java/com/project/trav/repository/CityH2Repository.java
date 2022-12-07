package com.project.trav.repository;

import com.project.trav.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityH2Repository extends JpaRepository<City,Long> {

}

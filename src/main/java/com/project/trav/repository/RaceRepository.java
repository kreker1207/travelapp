package com.project.trav.repository;

import com.project.trav.model.dto.QueryRace;
import com.project.trav.model.entity.Race;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;


@Repository
public interface RaceRepository extends JpaRepository<Race, Long>, QuerydslPredicateExecutor<Race>{

  Optional<Race> findByRaceNumber(String raceNumber);
}

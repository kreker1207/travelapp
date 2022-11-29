package com.project.trav.repository;

import com.project.trav.model.entity.QRace;
import com.project.trav.model.entity.Race;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;


@Repository
public interface RaceRepository extends JpaRepository<Race, Long>, QuerydslPredicateExecutor<Race>,
    QuerydslBinderCustomizer<QRace> {
  @Override
  default void customize(QuerydslBindings bindings,QRace race){
    bindings.bind(String.class).first((SingleValueBinding< StringPath,String >) StringExpression::containsIgnoreCase);
    bindings.bind(race.departureDateTime).all(((path, value) -> {
      Iterator<? extends LocalDateTime> iterator = value.iterator();
      LocalDateTime from = iterator.next();
      if (value.size() >=2){
        LocalDateTime to = iterator.next();
        return Optional.of(path.between(from,to));
      }else {
        return Optional.of(path.goe(from));
      }
    }));
  }
  Optional<Race> findByRaceNumber(String raceNumber);
}

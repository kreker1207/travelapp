package com.project.trav.repository;

import com.project.trav.model.entity.Ticket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
  Optional<Ticket> findByPlaceAndRaces_RaceNumber(String place, String raceNumber);
}

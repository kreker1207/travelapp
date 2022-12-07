package com.project.trav.repository;

import com.project.trav.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketH2Repository extends JpaRepository<Ticket,Long> {

}

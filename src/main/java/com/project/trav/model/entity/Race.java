package com.project.trav.model.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;


@Data
@Entity
@Accessors(chain = true)
@Table(name = "race")
public class Race {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToMany(
      mappedBy = "races",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<Ticket> tickets;
  @Column(name = "departure_date_time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalDateTime departureDateTime;
  @Column(name = "arrival_date_time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalDateTime arrivalDateTime;
  @Column(name = "travel_time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalTime travelTime;
  @Column(name = "airline")
  private String airline;
  @Column(name = "race_number")
  private String raceNumber;
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "departure_city", referencedColumnName = "id")
  private City departureCity;
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "arrival_city", referencedColumnName = "id")
  private City arrivalCity;
}

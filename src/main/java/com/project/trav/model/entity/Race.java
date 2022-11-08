package com.project.trav.model.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import javax.persistence.ManyToOne;
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
  @Column(name = "departure_date_time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalDateTime departureDateTime;
  @Column(name = "arrival_date_time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalDateTime arrivalDateTime;
  @Column(name = "duration")
  private Duration travelTimeDuration;
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

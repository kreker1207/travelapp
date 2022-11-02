package com.project.trav.model.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
  @Column(name = "departure_time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalDateTime departureTime;
  @Column(name = "arrival_time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalDateTime arrivalTime;
  @Column(name = "travel_time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalTime travelTime;
  @Column(name = "airline")
  private String airline;
  @Column(name = "race_number")
  private String raceNumber;
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "departure_city_id", referencedColumnName = "id")
  private City departureCityId;
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "arrival_city_id", referencedColumnName = "id")
  private City arrivalCityId;

}

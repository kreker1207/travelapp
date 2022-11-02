package com.project.trav.model.entity;

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
  private String departureTime;
  @Column(name = "arrival_time")
  private String arrivalTime;
  @Column(name = "travel_time")
  private String travelTime;
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

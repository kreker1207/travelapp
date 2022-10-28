package com.project.trav.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalTime;


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
    private LocalTime departureTime;
    @Column(name = "arrival_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime arrivalTime;
    @Column(name = "departure_city")
    private String departureCity;
    @Column(name = "arrival_city")
    private String arrivalCity;
    @Column(name = "travel_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime travelTime;
    @Column(name = "airline")
    private String airline;
    @Column(name = "race_number")
    private String raceNumber;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, unique = true)
    private City departureCityId;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id",referencedColumnName = "id",nullable = false,unique = true)
    private City arrivalCityId;

}

package com.project.trav.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @Column(name = "departure_city")
    private String departureCity;
    @Column(name = "arrival_city")
    private String arrivalCity;
    @Column(name = "travel_time")
    private String travelTime;
    @Column(name = "airline")
    private String airline;
    @Column(name = "race_number")
    private String raceNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Race race = (Race) o;
        return id != null && Objects.equals(id, race.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

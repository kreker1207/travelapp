package com.project.trav.model.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(
      mappedBy = "races",
      cascade = CascadeType.ALL
  )
  private List<Ticket> tickets = new ArrayList<>();
  @Column(name = "departure_date_time")
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
  private LocalDateTime departureDateTime;
  @Column(name = "arrival_date_time")
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
  private LocalDateTime arrivalDateTime;
  @Column(name = "duration")
  private Duration travelTimeDuration;
  @Column(name = "airline")
  private String airline;
  @Column(name = "race_number")
  private String raceNumber;
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "departure_city_id", referencedColumnName = "id")
  private City departureCity;
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "arrival_city_id", referencedColumnName = "id")
  private City arrivalCity;
}

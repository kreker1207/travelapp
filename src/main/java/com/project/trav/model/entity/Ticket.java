package com.project.trav.model.entity;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "ticket")
public class Ticket {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "user_id")
  private Long userId;
  @Column(name = "place")
  private String place;
  @Column(name = "place_class")
  private String placeClass;
  @Column(name = "cost")
  private String cost;
  @Enumerated(value = EnumType.STRING)
  @Column(name = "ticket_status")
  private TicketStatus ticketStatus;
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "race_id", referencedColumnName = "id")
  private Race races;

}

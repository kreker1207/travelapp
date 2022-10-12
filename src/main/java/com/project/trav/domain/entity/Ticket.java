package com.project.trav.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
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
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id",referencedColumnName = "id",nullable = false,unique = true)
    private Race races;

}

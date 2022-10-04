package com.project.trav.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "race_id",referencedColumnName = "id",nullable = false,unique = true)
    private Race races;

}

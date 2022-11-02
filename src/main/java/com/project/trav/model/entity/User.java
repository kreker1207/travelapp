package com.project.trav.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "users")
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "surname")
  private String surname;
  @Column(name = "mail")
  private String mail;
  @Column(name = "phone")
  private String phone;
  @Column(name = "login")
  private String login;
  @JsonIgnore
  @Column(name = "password")
  private String password;
  @Enumerated(value = EnumType.STRING)
  @Column(name = "role")
  private Role role;
  @Enumerated(value = EnumType.STRING)
  @Column(name = "status")
  private Status status;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private List<Ticket> tickets;
}

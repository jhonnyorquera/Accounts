package com.banco.cliente.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int idPerson;

  @Column
  private String name;

  @Column
  private String genre;

  @Column
  private Integer age;


  @Column(unique=true)
  private String identifier;

  @Column(unique=true)
  private String identifier_uuid;

  @Column
  private String address;

  @Column
  private String telephone;



}

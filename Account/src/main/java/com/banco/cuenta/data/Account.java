package com.banco.cuenta.data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {

  @Id
  @Column
  @GeneratedValue
  private Integer idAccount;

  @Column(unique = true)
  private String number;

  @Column
  private String type;

  @Column
  private double balance;

  @Column
  private Boolean status;

  @Column
  private String identifierUuid;

  @PrePersist
  public void prePersist() {
    if (status == null) {
      status = true;
    }
  }


}

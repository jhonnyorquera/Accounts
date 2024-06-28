package com.banco.cuenta.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "customer")
@PrimaryKeyJoinColumn(name ="idCustomer", referencedColumnName = "idPerson")
public class Customer extends Person implements Serializable {

  public Customer() {
  }

  @Column
  private String password;

  @Column
  private Boolean status;

  @PrePersist
  private void prePersist() {
    if (status == null) {
      status = true;
    }
  }

}

package com.banco.cuenta.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movement {

  @Id
  @GeneratedValue
  @Column
  private Integer idMovement;

  @Column
  private String type;

  @Column double amount;

  @Column double balance;

  @ManyToOne
  @JsonIgnore
  private Account account;

  @Column
  private LocalDate dateMovement;

  @PrePersist
  private void prePersist() {
    if (type == null) {
      type = "CREDIT";
    }
    if (this.dateMovement == null) {
      this.dateMovement = LocalDate.now();
    }
  }


}

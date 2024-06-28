package com.banco.cuenta.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MovementDto {

  public MovementDto() {

  }

  private String accountNumber;
  @NotNull
  private Double movement;
}

package com.banco.cuenta.dto;


import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountDto implements Serializable {

  @NotBlank
  private String number;
  @NotBlank
  private String type;
  @NotBlank
  private double balance;
  @NotBlank
  private String identityCustomer;

  public AccountDto() {

  }
}

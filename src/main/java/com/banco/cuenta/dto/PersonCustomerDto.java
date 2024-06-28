package com.banco.cuenta.dto;


import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class PersonCustomerDto implements Serializable {

  @NotBlank
  private String name;
  private String address;
  private String telephone;
  private String password;
  @NotBlank
  private String identifier;
  private Integer age;
  private String genre;

  public PersonCustomerDto() {

  }
}

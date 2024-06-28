package com.banco.cuenta.dto;

import com.banco.cuenta.data.Account;
import com.banco.cuenta.data.Movement;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AccountReportDto {

  private Account account;
  private List<Movement> movement;

}

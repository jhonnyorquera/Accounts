package com.banco.cuenta.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportMovementDto {

  private PersonCustomerDto customer;

  private List<AccountReportDto> accountList;


}

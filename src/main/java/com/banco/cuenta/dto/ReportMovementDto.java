package com.banco.cuenta.dto;

import com.banco.cuenta.data.Customer;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportMovementDto {

  private Customer customer;

  private List<AccountReportDto> accountList;


}

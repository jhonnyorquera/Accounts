package com.banco.cuenta.services.impl;

import com.banco.cuenta.dto.AccountReportDto;
import com.banco.cuenta.dto.PersonCustomerDto;
import com.banco.cuenta.dto.ReportMovementDto;
import com.banco.cuenta.exception.CustomRuntimeException;
import com.banco.cuenta.integration.CustomerClient;
import com.banco.cuenta.repository.AccountRepository;
import com.banco.cuenta.repository.MovementRepository;
import com.banco.cuenta.services.ReportService;
import com.banco.cuenta.data.Account;
import com.banco.cuenta.data.Movement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

  private AccountRepository accountRepository;
  private MovementRepository movementRepository;
  private CustomerClient customerClient;


  /**
   * get movement report by filters
   * @param identity filter by identity user
   * @param dateBegin date begin report
   * @param dateEnd date end report
   * @return
   */
  @Override
  public ReportMovementDto getReportMovements(String identity, String dateBegin, String dateEnd) {
    Map<String, String> map = Map.of("identifier", identity);

    PersonCustomerDto customer = customerClient.getConsumer(map);
    List<Account> accounts = accountRepository.findByIdentifierUuid(customer.getIdentifierUuid());

    List<AccountReportDto> acountDto = new ArrayList<>();
    accounts.stream().forEach(account -> {
      AccountReportDto acc = new AccountReportDto();
      acc.setAccount(account);
      acc.setMovement(movementsBetween(account, dateBegin, dateEnd));
      acountDto.add(acc);
    });

    ReportMovementDto report = new ReportMovementDto();
    report.setCustomer(customer);
    report.setAccountList(acountDto);
    return report;

  }

  /**
   * Filter movement by date
   * @param account account whit movements
   * @param dateBegin
   * @param dateEnd
   * @return
   */
  private List<Movement> movementsBetween(Account account, String dateBegin, String dateEnd) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
    LocalDate startDate = LocalDate.parse(dateBegin, formatter);
    LocalDate endDate = LocalDate.parse(dateEnd, formatter);
    if (endDate.isBefore(startDate)) {
      throw new CustomRuntimeException("End date should not before than begin date", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    return movementRepository.findByDateMovementBetweenAndAccount(startDate, endDate, account);

  }


}

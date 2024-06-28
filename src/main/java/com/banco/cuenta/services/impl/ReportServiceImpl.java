package com.banco.cuenta.services.impl;

import com.banco.cuenta.data.Customer;
import com.banco.cuenta.dto.AccountReportDto;
import com.banco.cuenta.dto.ReportMovementDto;
import com.banco.cuenta.repository.AccountRepository;
import com.banco.cuenta.repository.CustomerRepository;
import com.banco.cuenta.repository.MovementRepository;
import com.banco.cuenta.services.ReportService;
import com.banco.cuenta.data.Account;
import com.banco.cuenta.data.Movement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

  private CustomerRepository customerRepository;
  private AccountRepository accountRepository;
  private MovementRepository movementRepository;


  /**
   * get movement report by filters
   * @param identity filter by identity user
   * @param dateBegin date begin report
   * @param dateEnd date end report
   * @return
   */
  @Override
  public ReportMovementDto getReportMovements(String identity, String dateBegin, String dateEnd) {
    Customer customer = customerRepository.findByIdentifier(identity);
    List<Account> accounts = accountRepository.findByCustomer(customer);

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

    return movementRepository.findByDateMovementBetweenAndAccount(startDate, endDate, account);

  }


}

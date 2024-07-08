package com.banco.cuenta.services.impl;

import com.banco.cuenta.data.Account;
import com.banco.cuenta.data.Movement;
import com.banco.cuenta.dto.AccountDto;
import com.banco.cuenta.dto.PersonCustomerDto;
import com.banco.cuenta.exception.CustomRuntimeException;
import com.banco.cuenta.integration.CustomerClient;
import com.banco.cuenta.repository.AccountRepository;
import com.banco.cuenta.repository.MovementRepository;
import com.banco.cuenta.services.AccountService;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;


@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

  private AccountRepository accountRepository;
  private MovementRepository movementRepository;
  private final ModelMapper modelMapper;
  private CustomerClient customerClient;

  @Override
  public void saveAccount(ArrayList<AccountDto> accountsDto) {
    accountsDto.forEach(this::saveAccount);
  }

  /**
   * Save an account validating if a customer and account exist
   *
   * @param accountDto dto for request
   */
  private void saveAccount(AccountDto accountDto) {
    Map<String, String> map = Map.of("identifier", accountDto.getIdentityCustomer());

    PersonCustomerDto personCustomerDto=  Optional.ofNullable(customerClient.getConsumer(map))
        .orElseThrow(() -> new RuntimeException("Customer not found whith identifier: " + accountDto.getIdentityCustomer()));

    Account account = accountRepository.findByNumber(accountDto.getNumber());

    if (account != null) {
      throw new CustomRuntimeException("Account number duplicated", HttpStatus.CONFLICT);
    }

    Account accounts = accountRepository.save(mappingAccount(accountDto, personCustomerDto.getIdentifierUuid()));
    saveFirsMovement(accounts, accountDto.getBalance());


  }

  private void saveFirsMovement(Account account, Double amount) {
    Movement movement = Movement.builder()
        .account(account).amount(amount)
        .balance(amount).build();
    movementRepository.save(movement);
  }


  private Account mappingAccount(AccountDto accountDto, String identifier_uuid) {
    Account account = modelMapper.map(accountDto, Account.class);
    account.setIdentifierUuid(identifier_uuid);
    account.setBalance(accountDto.getBalance());
    return account;

  }

  @Override
  public AccountDto findAccount(String number) {
    Account accountFound = Optional.ofNullable(accountRepository.findByNumber(number))
        .orElseThrow(() -> new RuntimeException("No se encuentra cuenta con número: " + number));
    return mappingAccount(accountFound);
  }

  @Override
  public void deleteAccount(String number) {
    Account account = Optional.ofNullable(accountRepository.findByNumber(number))
        .orElseThrow(() -> new RuntimeException("No se encuentra cuenta con número: " + number));
    account.setStatus(Boolean.FALSE);
    accountRepository.save(account);
  }


  private AccountDto mappingAccount(Account account) {
    Map<String, String> map = Map.of("uuid", account.getIdentifierUuid());
    PersonCustomerDto personCustomerDto=  Optional.ofNullable(customerClient.getConsumer(map))
        .orElseThrow(() -> new RuntimeException("Cliente no encontrado con identificacion: "));
    AccountDto accountDto = modelMapper.map(account, AccountDto.class);
    accountDto.setType(account.getType().equals("CA") ? "AHORROS" : "CORRIENTE");
    accountDto.setIdentityCustomer(personCustomerDto.getIdentifier());
    return accountDto;
  }

}

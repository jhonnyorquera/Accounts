package com.banco.cuenta.services.impl;

import com.banco.cuenta.data.Account;
import com.banco.cuenta.data.Movement;
import com.banco.cuenta.dto.MovementDto;
import com.banco.cuenta.exception.CustomRuntimeException;
import com.banco.cuenta.repository.AccountRepository;
import com.banco.cuenta.repository.MovementRepository;
import com.banco.cuenta.services.MovementService;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovementServiceImpl implements MovementService {

  private AccountRepository accountRepository;
  private MovementRepository movementRepository;


  /**
   * save a movement for type movement debit or credit
   * @param movementDto
   */
  @Override
  public String saveMovement(MovementDto movementDto) {
    Account account = accountRepository.findByNumber(movementDto.getAccountNumber());
    if (account.equals(null)  || Double.compare(Math.abs(movementDto.getMovement()), 0) == 0) {
      throw new CustomRuntimeException("Customer not exist or movement value is zero", HttpStatus.UNPROCESSABLE_ENTITY);
    }
      if (Double.compare(movementDto.getMovement(), 0) > 0) {
        return saveCredit(account, movementDto);
      } else {
        return saveDebit(account, movementDto);
      }
  }

  private String saveDebit(Account account, MovementDto movementDto) {
    if (Double.compare(account.getBalance(), Math.abs(movementDto.getMovement())) >= 0) {
     return saveCredit(account, movementDto);
    } else {
      throw new CustomRuntimeException("insufficient balance", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  private String saveCredit(Account account, MovementDto movementDto) {
    Movement mov = mapAccount(account, movementDto);
    mov.setAmount(mov.getAmount());
    mov.setBalance(account.getBalance()+movementDto.getMovement());
    movementRepository.save(mov);
    saveAccount(account, mov);
    return "Actual Balance : " +String.valueOf(mov.getBalance());
  }

  private Movement mapAccount(Account account, MovementDto movementDto) {
    Movement movement = Movement.builder()
        .account(account)
        .type(movementDto.getMovement() < 0 ? "DEBIT" : "CREDIT")
        .amount(movementDto.getMovement())
        .balance(0).build();
    return movement;
  }


  private void saveAccount(Account account, Movement movement) {
    account.setBalance(account.getBalance() + movement.getAmount());
    accountRepository.save(account);

  }
}




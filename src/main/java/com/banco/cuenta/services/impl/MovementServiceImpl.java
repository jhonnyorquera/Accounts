package com.banco.cuenta.services.impl;

import com.banco.cuenta.data.Account;
import com.banco.cuenta.data.Movement;
import com.banco.cuenta.dto.MovementDto;
import com.banco.cuenta.repository.AccountRepository;
import com.banco.cuenta.repository.MovementRepository;
import com.banco.cuenta.services.MovementService;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
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
  public void saveMovement(MovementDto movementDto) {
    Account account = accountRepository.findByNumber(movementDto.getAccountNumber());
    if (account.equals(null)  || Double.compare(Math.abs(movementDto.getMovement()), 0) == 0) {
      throw new RuntimeException("no existe cliente o el valor del movimiento es 0");
    }
      if (Double.compare(movementDto.getMovement(), 0) > 0) {
        saveCredit(account, movementDto);
      } else {
        saveDebit(account, movementDto);
      }
  }

  private void saveDebit(Account account, MovementDto movementDto) {
    if (Double.compare(account.getBalance(), Math.abs(movementDto.getMovement())) >= 0) {
      saveCredit(account, movementDto);
    } else {
      throw new RuntimeException("Saldo insuficiente");
    }
  }

  private void saveCredit(Account account, MovementDto movementDto) {
    Movement mov = mapAccount(account, movementDto);
    mov.setAmount(mov.getAmount());
    mov.setBalance(account.getBalance()+movementDto.getMovement());
    movementRepository.save(mov);
    saveAccount(account, mov);

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




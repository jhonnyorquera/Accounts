package com.banco.cuenta.repository;

import com.banco.cuenta.data.Account;
import com.banco.cuenta.data.Movement;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Integer> {

  List<Movement> findByAccount(Account account);

  List<Movement> findByDateMovementBetweenAndAccount(LocalDate dateBegin, LocalDate dateEnd, Account account);


}

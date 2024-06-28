package com.banco.cuenta.repository;

import com.banco.cuenta.data.Account;
import com.banco.cuenta.data.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

  Account findByNumber(String number);

  List<Account> findByCustomer(Customer customer);


}

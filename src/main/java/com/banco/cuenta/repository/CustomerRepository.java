package com.banco.cuenta.repository;

import com.banco.cuenta.data.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

   Customer findByIdentifier(String identity);

}

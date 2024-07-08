package com.banco.cliente.repository;

import com.banco.cliente.data.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

   Customer findByIdentifier(String identity);

}

package com.banco.cliente.services.impl;

import com.banco.cliente.data.Customer;
import com.banco.cliente.dto.PersonCustomerDto;
import com.banco.cliente.repository.CustomerRepository;
import com.banco.cliente.services.CustomerService;
import java.util.ArrayList;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  private final ModelMapper modelMapper;

  /**
   * Save a customer
   *
   * @param personCustomerDtos dto request
   */
  @Override
  public void saveCustomers(ArrayList<PersonCustomerDto> personCustomerDtos) {
    personCustomerDtos.stream().forEach(personCustomerDto -> {
      try {
        customerRepository.save(modelMapper.map(personCustomerDto, Customer.class));
      } catch (RuntimeException runtimeException) {
        throw new RuntimeException("Identity duplicado");
      }
    });
  }

  @Override
  public void deleteCustomer(String identifier){
    Customer customer=  Optional.ofNullable(customerRepository.findByIdentifier(identifier))
        .orElseThrow(() -> new RuntimeException("Customer not found with identifier: " + identifier));
    customer.setStatus(Boolean.FALSE);
    customerRepository.save(customer);
  }


  @Override
  public PersonCustomerDto getCustomerByIdentity(String identity) {
    return modelMapper.map(this.customerRepository.findByIdentifier(identity), PersonCustomerDto.class);
  }

  @Override
  public void editCustomer(PersonCustomerDto personCustomerDto) {
    Customer customer=  Optional.ofNullable(customerRepository.findByIdentifier(personCustomerDto.getIdentifier()))
        .orElseThrow(() -> new RuntimeException("Customer not found with identifier: " +
            personCustomerDto.getIdentifier()));
    this.customerRepository.save(editingFieldCustomer(customer, personCustomerDto));

  }


  private Customer editingFieldCustomer(Customer customer, PersonCustomerDto personCustomerDto) {
    customer.setTelephone(personCustomerDto.getTelephone());
    customer.setGenre(personCustomerDto.getGenre());
    customer.setName(personCustomerDto.getName());
    customer.setAge(personCustomerDto.getAge());
    customer.setAddress(personCustomerDto.getAddress());
    customer.setPassword(personCustomerDto.getPassword());
    return customer;
  }


}

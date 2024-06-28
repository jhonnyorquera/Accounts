package com.banco.cuenta.controller;

import com.banco.cuenta.dto.PersonCustomerDto;
import com.banco.cuenta.services.CustomerService;
import java.util.ArrayList;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
@Validated
public class CustomerController {



  private CustomerService customerService;

  @PutMapping
  public ResponseEntity<String> saveCustomerPerson(@Valid
      @RequestBody ArrayList<PersonCustomerDto> personCustomerDto) {
    customerService.saveCustomers(personCustomerDto);
    return new ResponseEntity<>("Customer Created!", HttpStatus.CREATED);

  }

  @GetMapping
  public ResponseEntity<PersonCustomerDto> getCustomer(@RequestParam String identifier) {
    return new ResponseEntity<PersonCustomerDto>(customerService.getCustomerByIdentity(identifier),
        HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<String> getCustomer(@RequestBody PersonCustomerDto personCustomerDto) {
    customerService.editCustomer(personCustomerDto);
    return new ResponseEntity<>("Usuario Editado", HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<String> deleteCustomer(@RequestParam String identifier) {
    customerService.deleteCustomer(identifier);
    return new ResponseEntity<>("Customer deleted", HttpStatus.ACCEPTED);
  }


}

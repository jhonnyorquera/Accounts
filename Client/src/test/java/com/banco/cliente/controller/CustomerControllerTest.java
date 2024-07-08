package com.banco.cliente.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.banco.cliente.data.Customer;
import com.banco.cliente.dto.PersonCustomerDto;
import com.banco.cliente.repository.CustomerRepository;
import com.banco.cliente.services.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

  @LocalServerPort
  private int port;

  private String identifier = "1720508869";

  TestRestTemplate restTemplate = new TestRestTemplate();
  HttpHeaders headers = new HttpHeaders();

  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private CustomerService customerService;

  @BeforeClass
  public void init() {
    Customer customer = getCustomer();
    customer.setIdentifier("1720508869");
    Mockito.when(customerRepository.findByIdentifier("1720508869")).thenReturn(customer);
    Mockito.when(customerService.getCustomerByIdentity(identifier)).thenReturn(getCustomerDto());
  }


  @Test
  void getCustomerTs() {
    headers.setContentType(MediaType.APPLICATION_JSON);

    ObjectMapper objectMapper = new ObjectMapper();
    HttpEntity<String> entity = new HttpEntity<>((""), headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/api/banco/customer/?identifier=4488844"), HttpMethod.GET, entity, String.class);
    HttpStatus actual = response.getStatusCode();
    assertEquals( HttpStatus.OK, actual);
  }


  @Test
  void deleteCustomer() {
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> entity = new HttpEntity<>((""), headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/api/banco/custoddddmer/?identifier=1720508869"), HttpMethod.DELETE, entity,
        String.class);
    HttpStatus actual = response.getStatusCode();
    assertEquals(HttpStatus.NOT_FOUND, actual);

  }

  @Test
  void editCustomerPerson() throws JsonProcessingException {
    headers.setContentType(MediaType.APPLICATION_JSON);
    ObjectMapper objectMapper = new ObjectMapper();
    HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(getCustomerDto()), headers);
    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/api/bancfffffo/customer"), HttpMethod.POST, entity,
        String.class);
    HttpStatus actual = response.getStatusCode();
    assertEquals(HttpStatus.NOT_FOUND, actual);
  }




  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }


  private Customer getCustomer() {
    Customer customer = new Customer();
    customer.setIdentifier(this.identifier);
    customer.setName("Jhon Doe");
    customer.setPassword("passs");
    customer.setIdPerson(12);
    customer.setTelephone("2280629");
    return customer;
  }

  private PersonCustomerDto getCustomerDto() {
    PersonCustomerDto personCustomerDto = new PersonCustomerDto();
    personCustomerDto.setIdentifier(this.identifier);
    personCustomerDto.setName("Jhon Doe");
    personCustomerDto.setPassword("passs");
    personCustomerDto.setTelephone("2280629");
    return personCustomerDto;
  }
}
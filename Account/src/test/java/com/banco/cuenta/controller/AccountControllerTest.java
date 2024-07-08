package com.banco.cuenta.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.banco.cuenta.data.Account;
import com.banco.cuenta.data.Movement;
import com.banco.cuenta.dto.AccountDto;
import com.banco.cuenta.repository.AccountRepository;
import com.banco.cuenta.repository.MovementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {


  @LocalServerPort
  private int port;

  @Mock
  private AccountRepository accountRepository;


  @Mock
  private MovementRepository movementRepository;

  private String identifier = "1234455566";
  private String nroCuenta = "878999";
  private String uuid="a0173f46-fbad-4577-ab17-27495f7fdca9";


  TestRestTemplate restTemplate = new TestRestTemplate();

  HttpHeaders headers = new HttpHeaders();


  @BeforeClass
  public void init() {

    Mockito.when(accountRepository.findByNumber("199999999")).thenReturn(getAccountMock());
    Mockito.when(accountRepository.save(any())).thenReturn(getAccountMock());
    Mockito.when(movementRepository.save(any())).thenReturn(getMovement());

  }

  @Test
  void saveAccountWithoutBody() throws Exception {
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);
    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/api/something/customer/create"), HttpMethod.PUT, entity, String.class);

    HttpStatus actual = response.getStatusCode();

    assertEquals(actual, HttpStatus.NOT_FOUND);


  }


  @Test
  void getAccount() throws Exception {

    headers.setContentType(MediaType.APPLICATION_JSON);

    ObjectMapper objectMapper = new ObjectMapper();
    HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(getMockAccountDtoList()), headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/api/banco/accorrrrrunt/?number=12346999545"), HttpMethod.GET, entity, String.class);
    HttpStatus actual = response.getStatusCode();
    System.out.println("actual:    " + actual);
    assertEquals( HttpStatus.NOT_FOUND, actual);


  }

  @Test
  void deleteAccount() throws Exception {

      headers.setContentType(MediaType.APPLICATION_JSON);

      ObjectMapper objectMapper = new ObjectMapper();
      HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(getMockAccountDtoList()), headers);

      ResponseEntity<String> response = restTemplate.exchange(
          createURLWithPort("/api/banco/account/?number=12346999545"), HttpMethod.DELETE, entity, String.class);
      HttpStatus actual = response.getStatusCode();
      assertEquals(HttpStatus.NOT_FOUND, actual);



  }



  @Test
  void saveAccount() throws Exception {

    headers.setContentType(MediaType.APPLICATION_JSON);

    ObjectMapper objectMapper = new ObjectMapper();
    HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(getMockAccountDtoList()), headers);

    ResponseEntity<String> response = restTemplate.exchange(
    createURLWithPort("/api/naBanco/account/thisaccount"), HttpMethod.POST, entity, String.class);

    HttpStatus actual = response.getStatusCode();
    assertEquals(actual, HttpStatus.NOT_FOUND);


  }




  private AccountDto getMockAccountDto() {
    AccountDto accountDto = new AccountDto();
    accountDto.setNumber(this.nroCuenta);
    accountDto.setIdentityCustomer(this.identifier);
    accountDto.setBalance(200);
    accountDto.setType("C");
    return accountDto;

  }

  private List<AccountDto> getMockAccountDtoList() {
    ArrayList<AccountDto> accountDtos = new ArrayList<>();
    accountDtos.add(getMockAccountDto());
    return accountDtos;
  }


  private Account getAccountMock() {
    Account account = new Account();
    account.setStatus(Boolean.TRUE);
    account.setBalance(100);
    account.setIdAccount(1);
    account.setNumber(this.nroCuenta);
    account.setIdentifierUuid(uuid);
    return account;
  }

  private Movement getMovement() {
    Movement movement = Movement.builder()
        .account(getAccountMock())
        .amount(200)
        .balance(200)
        .type("CREDIT")
        .build();

    return movement;
  }

  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }


}
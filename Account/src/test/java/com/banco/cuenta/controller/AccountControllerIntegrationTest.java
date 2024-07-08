package com.banco.cuenta.controller;


import com.banco.cuenta.data.Account;
import com.banco.cuenta.dto.AccountDto;
import com.banco.cuenta.dto.PersonCustomerDto;
import com.banco.cuenta.integration.CustomerClient;
import com.banco.cuenta.repository.AccountRepository;
import com.banco.cuenta.services.AccountService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.banco.cuenta.services.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import java.util.Map;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.mockito.Mock;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


//@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebMvcTest(AccountController.class)
public class AccountControllerIntegrationTest {



  @LocalServerPort
  private int port;

  @MockBean
  private AccountRepository accountRepository;

  @MockBean
  private CustomerClient customerClient;

  @MockBean
  private ModelMapper modelMapper;


  @MockBean
  private AccountService accountService;

  TestRestTemplate restTemplate = new TestRestTemplate();
  HttpHeaders headers = new HttpHeaders();


  @Test
  public void testGetAccount() throws Exception {
    // Mock del repositorio y cliente
    Account mockAccount = new Account();
    mockAccount.setIdAccount(1);
    mockAccount.setBalance(200);
    mockAccount.setStatus(Boolean.TRUE);
    mockAccount.setIdentifierUuid("uuid123");
    mockAccount.setNumber("123456");
    mockAccount.setType("CA");

    when(accountRepository.findByNumber(anyString())).thenReturn(mockAccount);

    PersonCustomerDto mockPersonCustomerDto = new PersonCustomerDto();
    mockPersonCustomerDto.setIdentifier("222");

    when(customerClient.getConsumer(any(Map.class))).thenReturn(mockPersonCustomerDto);

    AccountDto mockAccountDto = new AccountDto();
    mockAccountDto.setNumber("999");
    mockAccountDto.setType("CA");
    mockAccountDto.setBalance(10);
    mockAccountDto.setIdentityCustomer("222");

    when(modelMapper.map(mockAccount, AccountDto.class)).thenReturn(mockAccountDto);

    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<AccountDto> response = restTemplate.exchange(
        createURLWithPort("/api/banco/account/?number=999"), HttpMethod.GET, entity, AccountDto.class);
    HttpStatus actual = response.getStatusCode();
    assertEquals(HttpStatus.OK, actual);


    // Ejecutar la solicitud HTTP y verificar la respuesta
   /* mockMvc.perform(get("/api/banco/account")
            .param("number", "999")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.number").value("123456"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("AHORROS"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.identityCustomer").value("222"));*/
  }


  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }

}

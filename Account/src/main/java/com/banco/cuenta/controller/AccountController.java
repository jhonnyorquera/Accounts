package com.banco.cuenta.controller;


import com.banco.cuenta.dto.AccountDto;
import com.banco.cuenta.services.AccountService;
import java.util.ArrayList;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

  @Autowired
  private AccountService accountService;

  @PutMapping
  public ResponseEntity<String> saveAccount(@Valid @RequestBody ArrayList<AccountDto> accountDtos){
  accountService.saveAccount(accountDtos);
    return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
  }




  @DeleteMapping
  public ResponseEntity<String> deleteAccount(@RequestParam String number) {
    accountService.deleteAccount(number);
    return new ResponseEntity<>("Account deleted", HttpStatus.ACCEPTED);
  }


  @GetMapping
  public ResponseEntity<AccountDto> getAccount(@RequestParam String number) {
    return new ResponseEntity<AccountDto>(accountService.findAccount(number), HttpStatus.OK);
  }

}

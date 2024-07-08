package com.banco.cuenta.services;

import com.banco.cuenta.dto.AccountDto;
import java.util.ArrayList;

public interface AccountService {

  void saveAccount(ArrayList<AccountDto> accountDtos);

  AccountDto findAccount(String number);

  void deleteAccount(String number);

}

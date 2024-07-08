package com.banco.cuenta.integration;

import com.banco.cuenta.dto.PersonCustomerDto;
import java.util.Map;

public interface CustomerClient {

  PersonCustomerDto getConsumer(Map<String, String> criteria);

}

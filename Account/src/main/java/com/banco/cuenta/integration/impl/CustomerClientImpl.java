package com.banco.cuenta.integration.impl;

import com.banco.cuenta.dto.PersonCustomerDto;
import com.banco.cuenta.integration.CustomerClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.StringJoiner;
import org.springframework.stereotype.Component;

@Component
public class CustomerClientImpl implements CustomerClient {



  @Override
  public PersonCustomerDto getConsumer(Map<String, String> criteria) {


    HttpClient client = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(10))
        .build();

    HttpRequest request = null;
    try {
          URI uri = buildURI("http://localhost:8081/api/banco/customer", criteria);
        request = HttpRequest.newBuilder()
          .uri(uri)
          .setHeader("Content-Type", "application/json")
          .GET()
          .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() == 200) {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(response.body(), PersonCustomerDto.class);
      }


    } catch (URISyntaxException | IOException | InterruptedException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private  URI buildURI(String baseUri, Map<String, String> params) throws Exception {
    StringJoiner sj = new StringJoiner("&");
    for (Map.Entry<String, String> entry : params.entrySet()) {
      sj.add(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()) + "=" +
          URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
    }
    String uriWithParams = baseUri + "?" + sj.toString();
    return new URI(uriWithParams);
  }


}

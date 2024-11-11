package com.picpaydesafio.demopicpaydesafio.application.services.imp;

import com.picpaydesafio.demopicpaydesafio.application.services.EmailValidatorService;
import com.picpaydesafio.demopicpaydesafio.web.dtos.EmailValidationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class EmailValidatorServiceImp implements EmailValidatorService {

  private final RestTemplate restTemplate;

  @Value("${abstract.api-key}")
  private String API_KEY;

  private final String API_URL = "https://emailvalidation.abstractapi.com/v1";

  @Override
  public boolean isValid(String email) {

    String url = UriComponentsBuilder.fromHttpUrl(API_URL)
        .queryParam("api_key", API_KEY)
        .queryParam("email", email)
        .toUriString();

    EmailValidationResponseDTO response = restTemplate.getForObject(url, EmailValidationResponseDTO.class);

    return response != null && response.isEmailValid();
  }


}

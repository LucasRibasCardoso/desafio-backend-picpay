package com.picpaydesafio.demopicpaydesafio.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class EmailValidatorServiceImp {

  private final RestTemplate restTemplate;
}

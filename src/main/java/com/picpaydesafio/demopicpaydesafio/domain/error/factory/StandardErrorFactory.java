package com.picpaydesafio.demopicpaydesafio.domain.error.factory;

import com.picpaydesafio.demopicpaydesafio.domain.error.DefaultError;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class StandardErrorFactory {

  public StandardError create(String message, HttpStatus status) {
    return new DefaultError(message, status.value());
  }

  public StandardError create(String message, HttpStatus status, Map<String, String> fieldErrors) {
    return new DefaultError(message, status.value(), fieldErrors);
  }
}

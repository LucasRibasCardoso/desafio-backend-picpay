package com.picpaydesafio.demopicpaydesafio.domain.factories.imp;


import com.picpaydesafio.demopicpaydesafio.domain.factories.StandardError;
import com.picpaydesafio.demopicpaydesafio.domain.models.DefaultError;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class StandardErrorFactoryImp {

  public StandardError create(String message, HttpStatus status) {
    return new DefaultError(message, status.value());
  }

  public StandardError create(String message, HttpStatus status, Map<String, String> fieldErrors) {
    return new DefaultError(message, status.value(), fieldErrors);
  }
}

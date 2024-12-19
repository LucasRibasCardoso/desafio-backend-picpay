package com.picpaydesafio.demopicpaydesafio.application.exceptions;

public class CustomTokenExpiredException extends RuntimeException {

  public CustomTokenExpiredException(String message) {
    super(message);
  }

}

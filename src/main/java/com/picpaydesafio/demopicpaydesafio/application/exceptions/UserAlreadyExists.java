package com.picpaydesafio.demopicpaydesafio.application.exceptions;

public class UserAlreadyExists extends RuntimeException {

  public UserAlreadyExists(String message) {
    super(message);
  }

}

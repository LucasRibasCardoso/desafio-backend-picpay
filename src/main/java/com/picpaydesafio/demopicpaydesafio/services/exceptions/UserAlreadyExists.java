package com.picpaydesafio.demopicpaydesafio.services.exceptions;

public class UserAlreadyExists extends RuntimeException {

  public UserAlreadyExists(String message) {
    super(message);
  }

}

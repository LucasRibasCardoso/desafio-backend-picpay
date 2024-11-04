package com.picpaydesafio.demopicpaydesafio.application.user.exceptions;

public class UserAlreadyExists extends RuntimeException {

  public UserAlreadyExists(String message) {
    super(message);
  }

}

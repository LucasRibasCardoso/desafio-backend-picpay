package com.picpaydesafio.demopicpaydesafio.services.exceptions;

public class InsufficientBalance extends RuntimeException {

  public InsufficientBalance(String message) {
    super(message);
  }

}

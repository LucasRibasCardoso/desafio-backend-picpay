package com.picpaydesafio.demopicpaydesafio.services.exceptions;

public class UnauthorizedTransaction extends RuntimeException {

  public UnauthorizedTransaction(String message) {
    super(message);
  }

}

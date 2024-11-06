package com.picpaydesafio.demopicpaydesafio.application.exceptions;

public class UnauthorizedTransactionException extends RuntimeException {

  public UnauthorizedTransactionException(String message) {
    super(message);
  }

}

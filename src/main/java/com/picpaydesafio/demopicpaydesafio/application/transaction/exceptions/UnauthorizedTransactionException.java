package com.picpaydesafio.demopicpaydesafio.application.transaction.exceptions;

public class UnauthorizedTransactionException extends RuntimeException {

  public UnauthorizedTransactionException(String message) {
    super(message);
  }

}

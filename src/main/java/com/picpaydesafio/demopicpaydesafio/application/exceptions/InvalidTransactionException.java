package com.picpaydesafio.demopicpaydesafio.application.exceptions;

public class InvalidTransactionException extends RuntimeException {

  public InvalidTransactionException(String message) {
    super(message);
  }

}
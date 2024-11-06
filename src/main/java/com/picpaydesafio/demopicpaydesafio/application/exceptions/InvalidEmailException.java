package com.picpaydesafio.demopicpaydesafio.application.exceptions;

public class InvalidEmailException extends RuntimeException {

  public InvalidEmailException(String message) {
    super(message);
  }
}

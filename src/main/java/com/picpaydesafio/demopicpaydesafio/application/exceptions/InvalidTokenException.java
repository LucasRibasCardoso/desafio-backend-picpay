package com.picpaydesafio.demopicpaydesafio.application.exceptions;

public class InvalidTokenException extends RuntimeException {
  public InvalidTokenException(String message) {
    super(message);
  }
}

package com.picpaydesafio.demopicpaydesafio.application.exceptions;

public class TokenExpiredException extends RuntimeException {

  public TokenExpiredException(String message) {
    super(message);
  }

}

package com.picpaydesafio.demopicpaydesafio.domain.models;

import com.picpaydesafio.demopicpaydesafio.domain.factories.StandardError;
import java.util.HashMap;
import java.util.Map;

public class DefaultError implements StandardError {
  private final String message;
  private final int statusCode;
  private final Map<String, String> fieldErrors;

  public DefaultError(String message, int statusCode) {
    this.message = message;
    this.statusCode = statusCode;
    this.fieldErrors = new HashMap<>();
  }

  public DefaultError(String message, int statusCode, Map<String, String> fieldErrors) {
    this.message = message;
    this.statusCode = statusCode;
    this.fieldErrors = fieldErrors;
  }

  @Override
  public Map<String, String> getFieldErrors() {
    return fieldErrors;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public int getStatusCode() {
    return statusCode;
  }

}

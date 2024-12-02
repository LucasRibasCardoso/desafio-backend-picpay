package com.picpaydesafio.demopicpaydesafio.domain.models;

import com.picpaydesafio.demopicpaydesafio.domain.factories.StandardError;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class DefaultError implements StandardError {
  private final String message;
  private final int code;
  private final Map<String, String> fieldErrors;

  public DefaultError(String message, int statusCode) {
    this.message = message;
    this.code = statusCode;
    this.fieldErrors = new HashMap<>();
  }

  public DefaultError(String message, int statusCode, Map<String, String> fieldErrors) {
    this.message = message;
    this.code = statusCode;
    this.fieldErrors = fieldErrors;
  }

}

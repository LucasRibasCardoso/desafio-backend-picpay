package com.picpaydesafio.demopicpaydesafio.domain.factories;


import java.util.Map;

public interface StandardError {
  String getMessage();
  int getStatusCode();
  Map<String, String> getFieldErrors();
}

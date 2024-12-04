package com.picpaydesafio.demopicpaydesafio.domain.factories;


import java.util.Map;

public interface StandardError {
  String getMessage();
  int getCode();
  Map<String, String> getFieldErrors();
}

package com.picpaydesafio.demopicpaydesafio.domain.error.factory;

import java.util.Map;

public interface StandardError {
  String getMessage();
  int getStatusCode();
  Map<String, String> getFieldErrors();
}

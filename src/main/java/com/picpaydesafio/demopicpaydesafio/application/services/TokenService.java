package com.picpaydesafio.demopicpaydesafio.application.services;

import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserRole;

public interface TokenService {
  String generateToken(String email, UserRole role);
  String validateToken(String token);
}

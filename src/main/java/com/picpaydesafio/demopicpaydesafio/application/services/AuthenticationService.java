package com.picpaydesafio.demopicpaydesafio.application.services;

import com.picpaydesafio.demopicpaydesafio.web.dtos.AuthenticationDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.LoginResponseDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserRequestDTO;

public interface AuthenticationService {
  LoginResponseDTO login(AuthenticationDTO requestDTO);
  void register(UserRequestDTO requestDTO);
}

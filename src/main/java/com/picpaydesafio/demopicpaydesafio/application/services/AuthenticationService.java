package com.picpaydesafio.demopicpaydesafio.application.services;

import com.picpaydesafio.demopicpaydesafio.web.dtos.LoginRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.LoginResponseDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.RegisterResponseDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserRequestDTO;

public interface AuthenticationService {
  LoginResponseDTO login(LoginRequestDTO requestDTO);
  RegisterResponseDTO register(UserRequestDTO requestDTO);
}

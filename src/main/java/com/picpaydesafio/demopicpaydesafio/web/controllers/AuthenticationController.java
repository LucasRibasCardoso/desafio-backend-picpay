package com.picpaydesafio.demopicpaydesafio.web.controllers;

import com.picpaydesafio.demopicpaydesafio.application.services.AuthenticationService;
import com.picpaydesafio.demopicpaydesafio.web.dtos.LoginRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.LoginResponseDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.RegisterResponseDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO requestDTO) {
    LoginResponseDTO responseTokenJWT = authenticationService.login(requestDTO);
    return ResponseEntity.ok(responseTokenJWT);
  }

  @PostMapping("/register")
  public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid UserRequestDTO requestDTO) {
    RegisterResponseDTO response = authenticationService.register(requestDTO);

    return ResponseEntity.ok(response);
  }
}

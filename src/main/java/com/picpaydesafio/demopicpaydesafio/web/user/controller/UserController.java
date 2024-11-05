package com.picpaydesafio.demopicpaydesafio.web.user.controller;

import com.picpaydesafio.demopicpaydesafio.application.user.service.UserService;
import com.picpaydesafio.demopicpaydesafio.web.user.dtos.UserRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.user.dtos.UserResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserResponseDTO> insertNewUser(@RequestBody @Valid UserRequestDTO userRequest) {
    UserResponseDTO newUserResponse = userService.saveNewUser(userRequest);
    return new ResponseEntity<>(newUserResponse, HttpStatus.CREATED);
  }
}

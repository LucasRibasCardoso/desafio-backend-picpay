package com.picpaydesafio.demopicpaydesafio.web.controllers;

import com.picpaydesafio.demopicpaydesafio.application.services.UserService;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
    List<UserResponseDTO> users = userService.findAllUsers();
    return new ResponseEntity<>(users, HttpStatus.OK);
  }
}

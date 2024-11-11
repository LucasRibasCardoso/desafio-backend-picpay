package com.picpaydesafio.demopicpaydesafio.application.services;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserResponseDTO;
import java.util.List;

public interface UserService {

  List<UserResponseDTO> findAllUsers();
  User findUserById(Long id);
  void saveUsersWithNewBalances(User sender, User receiver);
  UserResponseDTO saveNewUser(UserRequestDTO userRequest);

}

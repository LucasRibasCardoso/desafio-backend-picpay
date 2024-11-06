package com.picpaydesafio.demopicpaydesafio.application.services;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserNotFound;
import com.picpaydesafio.demopicpaydesafio.domain.user.factory.UserFactory;
import com.picpaydesafio.demopicpaydesafio.domain.user.model.User;
import com.picpaydesafio.demopicpaydesafio.domain.user.repository.UserRepository;
import com.picpaydesafio.demopicpaydesafio.infrastructure.user.mapper.UserMapper;
import com.picpaydesafio.demopicpaydesafio.web.user.dtos.UserRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.user.dtos.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserFactory userFactory;
  private final UserMapper userMapper;

  public User findUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFound("Usuário com id " + id + " não encontrado."));
  }

  public void saveUsersWithNewBalances(User sender, User receiver) {
    userRepository.save(sender);
    userRepository.save(receiver);
  }


  public UserResponseDTO saveNewUser(UserRequestDTO user) {
    User newUser = userFactory.createDomain(user);
    User savedUser = userRepository.save(newUser);
    return userMapper.toResponseDTO(savedUser);
  }

}

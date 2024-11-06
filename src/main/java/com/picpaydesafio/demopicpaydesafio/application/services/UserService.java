package com.picpaydesafio.demopicpaydesafio.application.services;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserNotFound;
import com.picpaydesafio.demopicpaydesafio.application.usecases.ValidateUserUseCase;
import com.picpaydesafio.demopicpaydesafio.domain.factories.interfaces.UserFactory;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesInterfaces.UserRepository;
import com.picpaydesafio.demopicpaydesafio.infrastructure.mappers.UserMapper;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserFactory userFactory;
  private final UserMapper userMapper;

  private final ValidateUserUseCase validateUserUseCase;

  public List<UserResponseDTO> findAllUsers() {
    return userRepository.findAll().stream().map(userMapper::toResponseDTO).toList();
  }

  public User findUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFound("Usuário com id " + id + " não encontrado."));
  }

  public void saveUsersWithNewBalances(User sender, User receiver) {
    userRepository.save(sender);
    userRepository.save(receiver);
  }


  public UserResponseDTO saveNewUser(UserRequestDTO userRequest) {
    User user = userFactory.createDomain(userRequest);

    validateUserUseCase.execute(user);

    User savedUser = userRepository.save(user);
    return userMapper.toResponseDTO(savedUser);
  }

}

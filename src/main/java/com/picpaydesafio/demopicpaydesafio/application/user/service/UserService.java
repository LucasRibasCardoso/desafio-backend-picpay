package com.picpaydesafio.demopicpaydesafio.application.user.service;

import com.picpaydesafio.demopicpaydesafio.application.user.exceptions.UserNotFound;
import com.picpaydesafio.demopicpaydesafio.domain.user.model.User;
import com.picpaydesafio.demopicpaydesafio.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

  private final UserRepository userRepository;

  public User findUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFound("Usuário com id " + id + " não encontrado."));
  }

  public void updateBalances(User sender, User receiver) {
    userRepository.save(sender);
    userRepository.save(receiver);
  }

}

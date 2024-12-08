package com.picpaydesafio.demopicpaydesafio.application.services.imp;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserNotFoundException;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesDomain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationServiceImp implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com o email: " + email));

    return user;

  }

}

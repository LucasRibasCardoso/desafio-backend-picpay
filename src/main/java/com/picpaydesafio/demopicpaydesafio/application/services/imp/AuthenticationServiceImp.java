package com.picpaydesafio.demopicpaydesafio.application.services.imp;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserAlreadyExistsException;
import com.picpaydesafio.demopicpaydesafio.application.services.AuthenticationService;
import com.picpaydesafio.demopicpaydesafio.application.services.EmailValidatorService;
import com.picpaydesafio.demopicpaydesafio.domain.factories.UserFactory;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesDomain.UserRepository;
import com.picpaydesafio.demopicpaydesafio.web.dtos.LoginRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.LoginResponseDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

  private final UserFactory userFactory;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final TokenServiceImp tokenService;
  private final EmailValidatorService emailValidatorService;


  @Override
  public LoginResponseDTO login(LoginRequestDTO requestDTO) {
    var dataLogin = new UsernamePasswordAuthenticationToken(requestDTO.email(), requestDTO.password());
    var auth = this.authenticationManager.authenticate(dataLogin);

    User user = (User) auth.getPrincipal();

    String token = tokenService.generateToken(user.getEmail(), user.getRole());
    return new LoginResponseDTO(token);
  }

  @Override
  public void register(UserRequestDTO requestDTO) {
    emailValidatorService.isValid(requestDTO.email());

    if (this.userRepository.findByEmail(requestDTO.email()).isPresent()) {
      throw new UserAlreadyExistsException("Esse email já está em uso. Tente utilizar outro email.");
    }

    String encryptedPassword = passwordEncoder.encode(requestDTO.password());
    User newUser = userFactory.createDomain(requestDTO, encryptedPassword);

    this.userRepository.save(newUser);
  }
}

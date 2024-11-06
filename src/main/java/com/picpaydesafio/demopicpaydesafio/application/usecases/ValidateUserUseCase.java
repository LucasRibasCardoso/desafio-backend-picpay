package com.picpaydesafio.demopicpaydesafio.application.usecases;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.InvalidEmailException;
import com.picpaydesafio.demopicpaydesafio.application.services.interfaces.EmailValidatorService;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesInterfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateUserUseCase {

  private final EmailValidatorService emailValidatorService;
  private final UserRepository userRepository;

  public void execute(User user) {
    validateUniqueDocument(user.getDocument());
    validateUniqueEmail(user.getEmail());
    validateEmailFormat(user.getEmail());
  }

  private void validateUniqueDocument(String document) {
    if (userRepository.findByDocument(document).isPresent()) {
      throw new InvalidEmailException("O documento informado já está cadastrado. Tente utilizar outro documento.");
    }
  }

  private void validateUniqueEmail(String email) {
    if (userRepository.findByEmail(email).isPresent()) {
      throw new InvalidEmailException("O e-mail informado já está cadastrado. Tente utilizar outro e-mail.");
    }
  }

  private void validateEmailFormat(String email) {
    if (!emailValidatorService.isValid(email)) {
      throw new InvalidEmailException("O e-mail informado é inválido. Tente utilizar outro e-mail.");
    }
  }

}

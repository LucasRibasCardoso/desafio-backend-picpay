package com.picpaydesafio.demopicpaydesafio.application.usecases;

import com.picpaydesafio.demopicpaydesafio.application.services.UserService;
import com.picpaydesafio.demopicpaydesafio.application.services.interfaces.EmailValidatorService;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateUserUseCase {

  private final EmailValidatorService emailValidatorService;
  private final UserService userService;

  public void execute(User user) {

  }

}

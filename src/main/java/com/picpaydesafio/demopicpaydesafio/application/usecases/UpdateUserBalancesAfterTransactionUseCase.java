package com.picpaydesafio.demopicpaydesafio.application.usecases;

import com.picpaydesafio.demopicpaydesafio.application.services.imp.UserServiceImp;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserBalancesAfterTransactionUseCase {

  private final UserServiceImp userService;

  public void execute(User sender, User receiver) {
    userService.saveUsersWithNewBalances(sender, receiver);
  }
}

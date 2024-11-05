package com.picpaydesafio.demopicpaydesafio.application.usecases;

import com.picpaydesafio.demopicpaydesafio.application.user.service.UserService;
import com.picpaydesafio.demopicpaydesafio.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserBalancesAfterTransactionUseCase {

  private final UserService userService;

  public void execute(User sender, User receiver) {
    userService.saveUsersWithNewBalances(sender, receiver);
  }
}

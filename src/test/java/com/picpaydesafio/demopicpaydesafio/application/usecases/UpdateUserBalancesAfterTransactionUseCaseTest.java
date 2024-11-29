package com.picpaydesafio.demopicpaydesafio.application.usecases;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.picpaydesafio.demopicpaydesafio.application.services.imp.UserServiceImp;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateUserBalancesAfterTransactionUseCaseTest {

  @Mock
  private UserServiceImp userService;

  @InjectMocks
  private UpdateUserBalancesAfterTransactionUseCase useCase;

  @Test
  void shouldCallUserServiceToSaveUsersWithNewBalances() {
    // Arrange
    User sender = mock(User.class);
    User receiver = mock(User.class);

    // Act
    useCase.execute(sender, receiver);

    // Assert
    verify(userService).saveUsersWithNewBalances(sender, receiver);
  }
}
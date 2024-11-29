package com.picpaydesafio.demopicpaydesafio.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.InvalidEmailException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserAlreadyExists;
import com.picpaydesafio.demopicpaydesafio.application.services.EmailValidatorService;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesDomain.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidateUserUseCaseTest {

  public static final String MAIL = "valid@email.com";
  public static final String PASSWORD = "12345";
  public static final String DOCUMENT = "12345678911";
  public static final String INVALID_EMAIL = "invalidemail";

  @InjectMocks
  private ValidateUserUseCase validateUserUseCase;

  @Mock
  private EmailValidatorService emailValidatorService;

  @Mock
  private UserRepository userRepository;

  @Test
  void validate_ShouldNotThrowExceptionWithValidUser() {
    // Arrange
    User user = mock(User.class);
    when(user.getDocument()).thenReturn(PASSWORD);
    when(user.getEmail()).thenReturn(MAIL);
    when(userRepository.findByDocument(PASSWORD)).thenReturn(Optional.empty());
    when(userRepository.findByEmail(MAIL)).thenReturn(Optional.empty());
    when(emailValidatorService.isValid(MAIL)).thenReturn(true);

    // Act & Assert
    assertDoesNotThrow(() -> validateUserUseCase.execute(user));
  }

  @Test
  void validate_ShouldThrowException_WhenDocumentAlreadyExists() {
    // Arrange
    User user = mock(User.class);

    when(user.getDocument()).thenReturn(PASSWORD);
    when(userRepository.findByDocument(PASSWORD)).thenReturn(Optional.of(user));

    // Act Assert
    UserAlreadyExists exception = assertThrows(
        UserAlreadyExists.class, () -> validateUserUseCase.execute(user));

    assertEquals(
        "O documento informado já está cadastrado. Tente utilizar outro documento.", exception.getMessage());
  }

  @Test
  void validate_ShouldThrowException_WhenEmailAlreadyExists() {
    // Arrange
    User user = mock(User.class);
    when(user.getDocument()).thenReturn(DOCUMENT);
    when(user.getEmail()).thenReturn(MAIL);
    when(userRepository.findByDocument(DOCUMENT)).thenReturn(Optional.empty());
    when(userRepository.findByEmail(MAIL)).thenReturn(Optional.of(user));

    InvalidEmailException exception = assertThrows(InvalidEmailException.class,
        () -> validateUserUseCase.execute(user)
    );

    assertEquals(
        "O e-mail informado já está cadastrado. Tente utilizar outro e-mail.", exception.getMessage());
  }

  @Test
  void validate_ShouldThrowException_WhenEmailFormatIsInvalid() {
    User user = mock(User.class);
    when(user.getDocument()).thenReturn(PASSWORD);
    when(user.getEmail()).thenReturn(INVALID_EMAIL);
    when(userRepository.findByDocument(PASSWORD)).thenReturn(Optional.empty());
    when(userRepository.findByEmail(INVALID_EMAIL)).thenReturn(Optional.empty());
    when(emailValidatorService.isValid(INVALID_EMAIL)).thenReturn(false);

    // Act & Assert
    InvalidEmailException exception = assertThrows(
        InvalidEmailException.class,
        () -> validateUserUseCase.execute(user)
    );

    assertEquals(
        "O e-mail informado é inválido. Tente utilizar outro e-mail.",
        exception.getMessage()
    );
  }

}
package com.picpaydesafio.demopicpaydesafio.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.InvalidEmailException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserAlreadyExistsException;
import com.picpaydesafio.demopicpaydesafio.application.services.EmailValidatorService;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesDomain.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidateUserUseCaseTest {

  public static final String INVALID_EMAIL = "invalidemail";
  public static final String DOCUMENT = "12312312312";
  public static final String EMAIL = "example@gmail.com";
  public static final String PASSWORD = "password";

  @InjectMocks
  private ValidateUserUseCase validateUserUseCase;

  @Mock
  private EmailValidatorService emailValidatorService;

  @Mock
  private UserRepository userRepository;

  private User mockUser;

  @BeforeEach
  void setUp() {
    mockUser = mock(User.class);
  }

  @Test
  void validate_ShouldNotThrowExceptionWithValidUser() {
    // Arrange
    when(mockUser.getDocument()).thenReturn(PASSWORD);
    when(mockUser.getEmail()).thenReturn(EMAIL);

    when(userRepository.findByDocument(PASSWORD)).thenReturn(Optional.empty());
    when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
    when(emailValidatorService.isValid(EMAIL)).thenReturn(true);

    // Act & Assert
    assertDoesNotThrow(() -> validateUserUseCase.execute(mockUser));
  }

  @Test
  void validate_ShouldThrowException_WhenDocumentAlreadyExists() {
    // Arrange
    when(mockUser.getDocument()).thenReturn(PASSWORD);
    when(userRepository.findByDocument(PASSWORD)).thenReturn(Optional.of(mockUser));

    // Act & Assert
    UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class,
        () -> validateUserUseCase.execute(mockUser)
    );

    assertEquals(
        "O documento informado já está cadastrado. Tente utilizar outro documento.", exception.getMessage());
  }

  @Test
  void validate_ShouldThrowException_WhenEmailAlreadyExists() {
    // Arrange
    when(mockUser.getDocument()).thenReturn(DOCUMENT);
    when(mockUser.getEmail()).thenReturn(EMAIL);

    when(userRepository.findByDocument(DOCUMENT)).thenReturn(Optional.empty());
    when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(mockUser));

    // Act & Assert
    InvalidEmailException exception = assertThrows(InvalidEmailException.class,
        () -> validateUserUseCase.execute(mockUser)
    );

    assertEquals(
        "O e-mail informado já está cadastrado. Tente utilizar outro e-mail.", exception.getMessage());
  }

  @Test
  void validate_ShouldThrowException_WhenEmailFormatIsInvalid() {
    when(mockUser.getDocument()).thenReturn(PASSWORD);
    when(mockUser.getEmail()).thenReturn(INVALID_EMAIL);

    when(userRepository.findByDocument(PASSWORD)).thenReturn(Optional.empty());
    when(userRepository.findByEmail(INVALID_EMAIL)).thenReturn(Optional.empty());
    when(emailValidatorService.isValid(INVALID_EMAIL)).thenReturn(false);

    // Act & Assert
    InvalidEmailException exception = assertThrows(InvalidEmailException.class,
        () ->validateUserUseCase.execute(mockUser)
    );

    assertEquals(
        "O e-mail informado é inválido. Tente utilizar outro e-mail.", exception.getMessage()
    );
  }

}
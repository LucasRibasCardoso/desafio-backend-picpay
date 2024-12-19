package com.picpaydesafio.demopicpaydesafio.web.exceptions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.CustomTokenExpiredException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.InsufficientFoundsException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.InvalidEmailException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.InvalidSendEmailException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.InvalidTokenException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.TokenGenerationException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.UnauthorizedTransactionException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserAlreadyExistsException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserNotFoundException;
import com.picpaydesafio.demopicpaydesafio.domain.factories.StandardError;
import com.picpaydesafio.demopicpaydesafio.domain.factories.imp.StandardErrorFactoryImp;
import com.picpaydesafio.demopicpaydesafio.domain.models.DefaultError;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
class  ExceptionsHandlerTest {

  public static final HttpStatus HTTP_STATUS_BAD_REQUEST = HttpStatus.BAD_REQUEST;
  public static final HttpStatus HTTP_STATUS_UNAUTHORIZED = HttpStatus.UNAUTHORIZED;
  public static final HttpStatus HTTP_STATUS_NOT_FOUND = HttpStatus.NOT_FOUND;
  public static final HttpStatus HTTP_STATUS_INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;


  @InjectMocks
  private ExceptionsHandler exceptionsHandler;

  @Mock
  StandardErrorFactoryImp errorFactory;

  @Test
  void testDataIntegrityViolationException() {
    StandardError mockError = new DefaultError("Usuário já cadastrado.", HTTP_STATUS_BAD_REQUEST.value()) ;
    when(errorFactory.create("Usuário já cadastrado.", HTTP_STATUS_BAD_REQUEST)).thenReturn(mockError);

    ResponseEntity<StandardError> response = exceptionsHandler.DataIntegrityViolationException();

    assertAll(
        () -> assertEquals(HTTP_STATUS_BAD_REQUEST, response.getStatusCode()),
        () -> assertEquals(response.getBody(), mockError)
    );
  }

  @Test
  void testUserAlreadyExistsException() {
    // Arrange
    UserAlreadyExistsException exception = new UserAlreadyExistsException("Usuário já existe.");
    StandardError mockError = new DefaultError(exception.getMessage(), HTTP_STATUS_BAD_REQUEST.value());
    when(errorFactory.create(exception.getMessage(), HTTP_STATUS_BAD_REQUEST)).thenReturn(mockError);

    // Act
    ResponseEntity<StandardError> response = exceptionsHandler.UserAlreadyExistsException(exception);

    // Assert
    assertAll(
        () -> assertEquals(HTTP_STATUS_BAD_REQUEST, response.getStatusCode()),
        () -> assertEquals(response.getBody(), mockError)
    );
  }

  @Test
  void testInsufficientFoundsException() {
    // Arrange
    InsufficientFoundsException exception = new InsufficientFoundsException("Saldo insuficiente.");
    StandardError mockError = new DefaultError(exception.getMessage(), HTTP_STATUS_BAD_REQUEST.value());
    when(errorFactory.create(exception.getMessage(), HTTP_STATUS_BAD_REQUEST)).thenReturn(mockError);

    // Act
    ResponseEntity<StandardError> response =
        exceptionsHandler.InsufficientFoundsExceptionException(exception);

    // Assert
    assertAll(
        () -> assertEquals(HTTP_STATUS_BAD_REQUEST, response.getStatusCode()),
        () -> assertEquals(response.getBody(), mockError)
    );
  }

  @Test
  void testUnauthorizedTransactionException() {
    // Arrange
    UnauthorizedTransactionException exception =
        new UnauthorizedTransactionException("Transação não autorizada.");

    StandardError mockError = new DefaultError(exception.getMessage(), HTTP_STATUS_UNAUTHORIZED.value());
    when(errorFactory.create(exception.getMessage(), HTTP_STATUS_UNAUTHORIZED)).thenReturn(mockError);

    // Act
    ResponseEntity<StandardError> response = exceptionsHandler.UnauthorizedTransactionException(exception);

    // Asset
    assertAll(
        () -> assertEquals(HTTP_STATUS_UNAUTHORIZED, response.getStatusCode()),
        () -> assertEquals(response.getBody(), mockError)
    );
  }

  @Test
  void testUserNotFoundException() {
    // Arrange
    UserNotFoundException exception = new UserNotFoundException("Usuário não encontrado.");
    StandardError mockError = new DefaultError(exception.getMessage(), HTTP_STATUS_NOT_FOUND.value());
    when(errorFactory.create(exception.getMessage(), HTTP_STATUS_NOT_FOUND)).thenReturn(mockError);

    // Act
    ResponseEntity<StandardError> response = exceptionsHandler.UserNotFoundException(exception);

    // Assert
    assertAll(
        () -> assertEquals(HTTP_STATUS_NOT_FOUND, response.getStatusCode()),
        () -> assertEquals(response.getBody(), mockError)
    );
  }

  @Test
  void testMethodArgumentNotValidException() {
    // Arrange
    BindingResult bindingResult = mock(BindingResult.class);
    MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

    FieldError fieldError = new FieldError("field", "fieldName", "Mensagem de erro");

    Map<String, String> expectedErrors = Map.of("fieldName", "Mensagem de erro");

    StandardError mockError = new DefaultError(
        "Erro de validação", HTTP_STATUS_BAD_REQUEST.value(), expectedErrors);

    when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
    when(errorFactory.create("Erro de validação", HTTP_STATUS_BAD_REQUEST, expectedErrors))
        .thenReturn(mockError);

    // Act
    ResponseEntity<StandardError> response = exceptionsHandler.MethodArgumentNotValidException(exception);

    // Assert
    assertAll(
        () -> assertEquals(HTTP_STATUS_BAD_REQUEST, response.getStatusCode()),
        () -> assertEquals(response.getBody(), mockError)
    );
  }

  @Test
  void testInvalidSendEmailException() {
    // Arrange
    InvalidSendEmailException exception = new InvalidSendEmailException("Erro ao enviar email.");
    StandardError mockError = new DefaultError(exception.getMessage(), HTTP_STATUS_BAD_REQUEST.value());
    when(errorFactory.create(exception.getMessage(), HTTP_STATUS_BAD_REQUEST)).thenReturn(mockError);

    // Act
    ResponseEntity<StandardError> response = exceptionsHandler.InvalidSendEmail(exception);

    // Assert
    assertAll(
         () -> assertEquals(HTTP_STATUS_BAD_REQUEST, response.getStatusCode()),
        () -> assertEquals(response.getBody(), mockError)
    );
  }

  @Test
  void testInvalidEmailException() {
    // Arrange
    InvalidEmailException exception = new InvalidEmailException("Email inválido.");
    StandardError mockError = new DefaultError(exception.getMessage(), HTTP_STATUS_BAD_REQUEST.value());
    when(errorFactory.create(exception.getMessage(), HTTP_STATUS_BAD_REQUEST)).thenReturn(mockError);

    // Act
    ResponseEntity<StandardError> response = exceptionsHandler.InvalidEmailException(exception);

    // Assert
    assertAll(
        () -> assertEquals(HTTP_STATUS_BAD_REQUEST, response.getStatusCode()),
        () -> assertEquals(response.getBody(), mockError)
    );
  }

  @Test
  void testCustomTokenExpiredException() {
    // Arrange
    CustomTokenExpiredException exception = new CustomTokenExpiredException("Token expirado.");
    StandardError mockError = new DefaultError(exception.getMessage(), HTTP_STATUS_UNAUTHORIZED.value());
    when(errorFactory.create(exception.getMessage(), HTTP_STATUS_UNAUTHORIZED)).thenReturn(mockError);

    // Act
    ResponseEntity<StandardError> response = exceptionsHandler.CustomTokenExpiredException(exception);

    // Assert
    assertAll(
        () -> assertEquals(HTTP_STATUS_UNAUTHORIZED, response.getStatusCode()),
        () -> assertEquals(response.getBody(), mockError)
    );
  }

  @Test
  void testInvalidTokenExpiredException() {
    // Arrange
    InvalidTokenException exception = new InvalidTokenException("Token invalid.");
    StandardError mockError = new DefaultError(exception.getMessage(), HTTP_STATUS_UNAUTHORIZED.value());
    when(errorFactory.create(exception.getMessage(), HTTP_STATUS_UNAUTHORIZED)).thenReturn(mockError);

    // Act
    ResponseEntity<StandardError> response = exceptionsHandler.InvalidTokenException(exception);

    // Assert
    assertAll(
        () -> assertEquals(HTTP_STATUS_UNAUTHORIZED, response.getStatusCode()),
        () -> assertEquals(response.getBody(), mockError)
    );
  }

  @Test
  void testTokenGenerationException() {
    // Arrange
    TokenGenerationException exception = new TokenGenerationException("Erro ao gerar token.");
    StandardError mockError = new DefaultError(
        exception.getMessage(), HTTP_STATUS_INTERNAL_SERVER_ERROR.value());
    when(errorFactory.create(exception.getMessage(), HTTP_STATUS_INTERNAL_SERVER_ERROR)).thenReturn(mockError);

    // Act
    ResponseEntity<StandardError> response = exceptionsHandler.TokenGenerationException(exception);

    // Assert
    assertAll(
        () -> assertEquals(HTTP_STATUS_INTERNAL_SERVER_ERROR, response.getStatusCode()),
        () -> assertEquals(response.getBody(), mockError)
    );
  }

  @Test
  void testException() {
    // Arrange
    Exception exception = new Exception("Erro genérico.");
    StandardError mockError = new DefaultError(exception.getMessage(), HTTP_STATUS_INTERNAL_SERVER_ERROR.value());
    when(errorFactory.create(exception.getMessage(), HTTP_STATUS_INTERNAL_SERVER_ERROR)).thenReturn(mockError);

    // Act
    ResponseEntity<StandardError> response = exceptionsHandler.Exception(exception);

    // Assert
    assertAll(
        () -> assertEquals(HTTP_STATUS_INTERNAL_SERVER_ERROR, response.getStatusCode()),
        () -> assertEquals(response.getBody(), mockError)
    );
  }

  @Test
  void testRuntimeException() {
    // Arrange
    RuntimeException exception = new RuntimeException("Erro genérico.");
    StandardError mockError = new DefaultError(exception.getMessage(), HTTP_STATUS_INTERNAL_SERVER_ERROR.value());
    when(errorFactory.create(exception.getMessage(), HTTP_STATUS_INTERNAL_SERVER_ERROR)).thenReturn(mockError);

    // Act
    ResponseEntity<StandardError> response = exceptionsHandler.RuntimeException(exception);

    // Assert
    assertAll(
        () -> assertEquals(HTTP_STATUS_INTERNAL_SERVER_ERROR, response.getStatusCode()),
        () -> assertEquals(response.getBody(), mockError)
    );
  }

}
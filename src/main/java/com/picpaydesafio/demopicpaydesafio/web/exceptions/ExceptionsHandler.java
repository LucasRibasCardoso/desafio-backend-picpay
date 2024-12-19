package com.picpaydesafio.demopicpaydesafio.web.exceptions;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.CustomTokenExpiredException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.InvalidEmailException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.InvalidSendEmailException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.InsufficientFoundsException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.InvalidTokenException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.TokenGenerationException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.UnauthorizedTransactionException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserAlreadyExistsException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserNotFoundException;
import com.picpaydesafio.demopicpaydesafio.domain.factories.imp.StandardErrorFactoryImp;
import com.picpaydesafio.demopicpaydesafio.domain.factories.StandardError;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionsHandler {

  private final StandardErrorFactoryImp errorFactory;

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<StandardError> DataIntegrityViolationException() {
    StandardError error = errorFactory.create("Usuário já cadastrado.", HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<StandardError> UserAlreadyExistsException(UserAlreadyExistsException e) {
    StandardError error = errorFactory.create(e.getMessage(), HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InsufficientFoundsException.class)
  public ResponseEntity<StandardError> InsufficientFoundsExceptionException(InsufficientFoundsException e) {
    StandardError error = errorFactory.create(e.getMessage(), HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UnauthorizedTransactionException.class)
  public ResponseEntity<StandardError> UnauthorizedTransactionException(UnauthorizedTransactionException e) {
    StandardError error = errorFactory.create(e.getMessage(), HttpStatus.UNAUTHORIZED);
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<StandardError> UserNotFoundException(UserNotFoundException e) {
    StandardError error = errorFactory.create(e.getMessage(), HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StandardError> MethodArgumentNotValidException(MethodArgumentNotValidException e) {
    Map<String, String> errors = new HashMap<>();
    for (var error : e.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }

    StandardError error = errorFactory.create("Erro de validação", HttpStatus.BAD_REQUEST, errors);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidSendEmailException.class)
  public ResponseEntity<StandardError> InvalidSendEmail(InvalidSendEmailException e) {
    StandardError error = errorFactory.create(e.getMessage(), HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidEmailException.class)
  public ResponseEntity<StandardError> InvalidEmailException(InvalidEmailException e) {
    StandardError error = errorFactory.create(e.getMessage(), HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CustomTokenExpiredException.class)
  public ResponseEntity<StandardError> CustomTokenExpiredException(CustomTokenExpiredException e) {
    StandardError error = errorFactory.create(e.getMessage(), HttpStatus.UNAUTHORIZED);
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(TokenGenerationException.class)
  public ResponseEntity<StandardError> TokenGenerationException(TokenGenerationException e) {
    StandardError error = errorFactory.create(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<StandardError> InvalidTokenException(InvalidTokenException e) {
    StandardError error = errorFactory.create(e.getMessage(), HttpStatus.UNAUTHORIZED);
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<StandardError> Exception(Exception e) {
    StandardError error = errorFactory.create(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<StandardError> RuntimeException(RuntimeException e) {
    StandardError error = errorFactory.create(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }


}

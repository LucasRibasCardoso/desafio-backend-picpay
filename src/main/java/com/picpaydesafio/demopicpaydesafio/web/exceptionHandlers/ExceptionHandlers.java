package com.picpaydesafio.demopicpaydesafio.web.exceptionHandlers;

import com.picpaydesafio.demopicpaydesafio.application.email.exceptions.InvalidSendEmail;
import com.picpaydesafio.demopicpaydesafio.application.transaction.exceptions.InvalidTransactionException;
import com.picpaydesafio.demopicpaydesafio.application.transaction.exceptions.InsufficientFoundsException;
import com.picpaydesafio.demopicpaydesafio.application.transaction.exceptions.UnauthorizedTransactionException;
import com.picpaydesafio.demopicpaydesafio.application.user.exceptions.UserAlreadyExists;
import com.picpaydesafio.demopicpaydesafio.application.user.exceptions.UserNotFound;
import com.picpaydesafio.demopicpaydesafio.domain.error.factory.StandardError;
import com.picpaydesafio.demopicpaydesafio.domain.error.factory.StandardErrorFactory;
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
public class ExceptionHandlers {

  private final StandardErrorFactory errorFactory;

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<StandardError> DataIntegrityViolationException(DataIntegrityViolationException e) {
    StandardError error = errorFactory.create("Usuário já cadastrado.", HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserAlreadyExists.class)
  public ResponseEntity<StandardError> UserAlreadyExistsException(UserAlreadyExists e) {
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

  @ExceptionHandler(UserNotFound.class)
  public ResponseEntity<StandardError> UserNotFoundException(UserNotFound e) {
    StandardError error = errorFactory.create(e.getMessage(), HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidTransactionException.class)
  public ResponseEntity<StandardError> InvalidTransactionException(InvalidTransactionException e) {
    StandardError error = errorFactory.create(e.getMessage(), HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
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

  @ExceptionHandler(InvalidSendEmail.class)
  public ResponseEntity<StandardError> InvalidSendEmail(InvalidSendEmail e) {
    StandardError error = errorFactory.create(e.getMessage(), HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
}

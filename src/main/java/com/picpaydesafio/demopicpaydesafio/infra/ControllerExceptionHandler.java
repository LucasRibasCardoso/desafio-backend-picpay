package com.picpaydesafio.demopicpaydesafio.infra;

import com.picpaydesafio.demopicpaydesafio.dtos.ExceptionDTO;
import com.picpaydesafio.demopicpaydesafio.services.exceptions.InsufficientBalance;
import com.picpaydesafio.demopicpaydesafio.services.exceptions.OfflineService;
import com.picpaydesafio.demopicpaydesafio.services.exceptions.UnauthorizedTransaction;
import com.picpaydesafio.demopicpaydesafio.services.exceptions.UserAlreadyExists;
import com.picpaydesafio.demopicpaydesafio.services.exceptions.UserNotFound;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {


  @ExceptionHandler(UserAlreadyExists.class)
  public ResponseEntity UserAlreadyExistsException(UserAlreadyExists e) {
    return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InsufficientBalance.class)
  public ResponseEntity InsufficientBalanceException(InsufficientBalance e) {
    return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UnauthorizedTransaction.class)
  public ResponseEntity UnauthorizedTransactionException(UnauthorizedTransaction e) {
    return buildErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(OfflineService.class)
  public ResponseEntity OfflineServiceException(OfflineService e) {
    return buildErrorResponse(e.getMessage(), HttpStatus.BAD_GATEWAY);
  }

  @ExceptionHandler(UserNotFound.class)
  public ResponseEntity UserNotFoundException(UserNotFound e) {
    return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity ConstraintViolationException(ConstraintViolationException e) {
    String field = extractFieldFromMessage(e);
    String errorMessage = "Esse " + field + " ja foi cadastrado.";

    return buildErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
  }

  private String extractFieldFromMessage(ConstraintViolationException e) {
    String message = e.getSQLException().getMessage();
    String field = "desconhecido";

    Pattern pattern = Pattern.compile("ON PUBLIC\\.\\w+\\((\\w+)");
    Matcher matcher = pattern.matcher(message);

    if (matcher.find()) {
      field = matcher.group(1);
    }

    return field;
  }


  private ResponseEntity<ExceptionDTO> buildErrorResponse(
      String message, HttpStatus status
  ) {

    ExceptionDTO newExceptionDTO = new ExceptionDTO(message, status);
    return ResponseEntity.status(status)
        .body(newExceptionDTO);
  }

}

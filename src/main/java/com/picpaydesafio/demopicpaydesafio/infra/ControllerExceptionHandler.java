package com.picpaydesafio.demopicpaydesafio.infra;

import com.picpaydesafio.demopicpaydesafio.dtos.ExceptionDTO;
import com.picpaydesafio.demopicpaydesafio.services.exceptions.InsufficientBalance;
import com.picpaydesafio.demopicpaydesafio.services.exceptions.OfflineService;
import com.picpaydesafio.demopicpaydesafio.services.exceptions.UnauthorizedTransaction;
import com.picpaydesafio.demopicpaydesafio.services.exceptions.UserAlreadyExists;
import com.picpaydesafio.demopicpaydesafio.services.exceptions.UserNotFound;
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




  private ResponseEntity<ExceptionDTO> buildErrorResponse(
      String message, HttpStatus status) {

    ExceptionDTO newExceptionDTO = new ExceptionDTO(message, status);
    return ResponseEntity.status(status).body(newExceptionDTO);
  }
}

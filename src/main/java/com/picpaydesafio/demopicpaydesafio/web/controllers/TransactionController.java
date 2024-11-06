package com.picpaydesafio.demopicpaydesafio.web.controllers;

import com.picpaydesafio.demopicpaydesafio.application.services.TransactionService;
import com.picpaydesafio.demopicpaydesafio.web.dtos.TransactionRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.TransactionResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;

  @PostMapping
  public ResponseEntity<TransactionResponseDTO> createTransaction(
      @RequestBody @Valid TransactionRequestDTO request
  ) {
    TransactionResponseDTO transaction = transactionService.createTransaction(request);
    return new ResponseEntity<>(transaction, HttpStatus.CREATED);
  }

  @GetMapping
  public List<TransactionResponseDTO> findAllTransactions() {
    return transactionService.getAllTransactions();
  }

}

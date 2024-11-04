package com.picpaydesafio.demopicpaydesafio.web.transaction.controller;

import com.picpaydesafio.demopicpaydesafio.application.transaction.service.TransactionService;
import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionResponseDTO;
import com.picpaydesafio.demopicpaydesafio.web.transaction.mapper.TransactionDTOMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;
  private final TransactionDTOMapper dtoMapper;

  @PostMapping
  public ResponseEntity<TransactionResponseDTO> createTransaction(
      @RequestBody @Valid TransactionRequestDTO request
  ) {
    Transaction transaction = transactionService.createTransaction(request);

    TransactionResponseDTO transactionResponse = dtoMapper.toDTO(transaction);
    return new ResponseEntity<>(transactionResponse, HttpStatus.CREATED);
  }

}

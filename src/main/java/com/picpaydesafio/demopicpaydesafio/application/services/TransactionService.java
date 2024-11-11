package com.picpaydesafio.demopicpaydesafio.application.services;

import com.picpaydesafio.demopicpaydesafio.web.dtos.TransactionRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.TransactionResponseDTO;
import java.util.List;

public interface TransactionService {

  TransactionResponseDTO createTransaction(TransactionRequestDTO request);
  List<TransactionResponseDTO> getAllTransactions();
}

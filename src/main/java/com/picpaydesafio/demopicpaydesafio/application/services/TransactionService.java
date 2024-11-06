package com.picpaydesafio.demopicpaydesafio.application.services;

import com.picpaydesafio.demopicpaydesafio.application.services.interfaces.EmailSendingService;
import com.picpaydesafio.demopicpaydesafio.application.usecases.CreateTransactionUseCase;
import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesInterfaces.TransactionRepository;
import com.picpaydesafio.demopicpaydesafio.infrastructure.mappers.TransactionMapper;
import com.picpaydesafio.demopicpaydesafio.web.dtos.TransactionRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.TransactionResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final TransactionMapper transactionMapper;

  private final CreateTransactionUseCase createTransactionUseCase;
  private final EmailSendingService emailService;

  @Transactional
  public TransactionResponseDTO createTransaction(TransactionRequestDTO request) {
    Transaction transaction = createTransactionUseCase.execute(request);

    emailService.sendEmail(transaction);

    return transactionMapper.toResponseDTO(transaction);
  }

  public List<TransactionResponseDTO> getAllTransactions() {
    return transactionRepository.findAll()
        .stream()
        .map(transactionMapper::toResponseDTO)
        .toList();
  }

}

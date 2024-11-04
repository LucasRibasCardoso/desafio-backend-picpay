package com.picpaydesafio.demopicpaydesafio.web.transaction.mapper;

import com.picpaydesafio.demopicpaydesafio.domain.transction.factory.TransactionFactory;
import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionDTOMapper {

  private final TransactionFactory transactionFactory;

  public TransactionResponseDTO toDTO(Transaction transaction) {
    return transactionFactory.createDTO(transaction);
  }
}

package com.picpaydesafio.demopicpaydesafio.domain.transction.factory;

import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.user.model.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.transation.entity.TransactionEntity;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionResponseDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransactionFactory {

  TransactionResponseDTO createDTO(Transaction transaction);

  Transaction craeteDomain (Long id, BigDecimal amount, User sender, User receiver, LocalDateTime timestamp);

  Transaction createDomain (TransactionRequestDTO request);

  Transaction createDomain (TransactionEntity entity);

  TransactionEntity createEntity(Transaction domain);
}

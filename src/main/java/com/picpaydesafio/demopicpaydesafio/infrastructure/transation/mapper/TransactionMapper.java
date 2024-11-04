package com.picpaydesafio.demopicpaydesafio.infrastructure.transation.mapper;

import com.picpaydesafio.demopicpaydesafio.domain.transction.factory.TransactionFactory;
import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import com.picpaydesafio.demopicpaydesafio.infrastructure.transation.entity.TransactionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMapper {

  private final TransactionFactory transactionFactory;

  public Transaction toDomain(TransactionEntity entity) {
    return transactionFactory.createDomain(entity);
  }

  public TransactionEntity toEntity(Transaction domain) {
    return transactionFactory.createEntity(domain);
  }
}

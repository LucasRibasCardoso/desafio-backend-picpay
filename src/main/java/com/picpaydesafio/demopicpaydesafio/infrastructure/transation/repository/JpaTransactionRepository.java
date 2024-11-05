package com.picpaydesafio.demopicpaydesafio.infrastructure.transation.repository;

import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.transction.repository.TransactionRepository;
import com.picpaydesafio.demopicpaydesafio.infrastructure.transation.entity.TransactionEntity;
import com.picpaydesafio.demopicpaydesafio.infrastructure.transation.mapper.TransactionMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaTransactionRepository implements TransactionRepository {

  private final TransactionJpaRepository jpaRepository;
  private final TransactionMapper mapper;

  @Override
  public Transaction save(Transaction transaction) {
    TransactionEntity entity = mapper.toEntity(transaction);
    entity = jpaRepository.save(entity);
    return mapper.toDomain(entity);
  }

  @Override
  public List<Transaction> findAll() {
    return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
  }

}

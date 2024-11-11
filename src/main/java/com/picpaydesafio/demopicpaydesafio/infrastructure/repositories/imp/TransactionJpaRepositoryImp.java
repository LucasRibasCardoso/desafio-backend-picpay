package com.picpaydesafio.demopicpaydesafio.infrastructure.repositories.imp;

import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesDomain.TransactionRepository;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.TransactionEntity;
import com.picpaydesafio.demopicpaydesafio.infrastructure.mappers.TransactionMapper;
import com.picpaydesafio.demopicpaydesafio.infrastructure.repositories.TransactionJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TransactionJpaRepositoryImp implements TransactionRepository {

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

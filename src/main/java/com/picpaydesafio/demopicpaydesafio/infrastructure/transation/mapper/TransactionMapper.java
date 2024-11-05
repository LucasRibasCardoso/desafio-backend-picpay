package com.picpaydesafio.demopicpaydesafio.infrastructure.transation.mapper;

import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import com.picpaydesafio.demopicpaydesafio.infrastructure.transation.entity.TransactionEntity;
import com.picpaydesafio.demopicpaydesafio.infrastructure.user.mapper.UserMapper;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMapper {

  private final UserMapper userMapper;

  public Transaction toDomain(TransactionEntity entity) {
    return new Transaction(
        entity.getId(),
        entity.getAmount(),
        userMapper.toDomain(entity.getSender()),
        userMapper.toDomain(entity.getReceiver()),
        entity.getTimestamp()
    );
  }

  public TransactionResponseDTO toResponseDTO(Transaction transaction) {
    return new TransactionResponseDTO(
        transaction.getId(),
        transaction.getSender().getId(),
        transaction.getSender().fullName(),
        transaction.getReceiver().getId(),
        transaction.getReceiver().fullName(),
        transaction.getAmount(),
        transaction.getTimestamp()
    );
  }

  public TransactionEntity toEntity(Transaction domain) {
    return new TransactionEntity(
        domain.getId(),
        domain.getAmount(),
        userMapper.toEntity(domain.getSender()),
        userMapper.toEntity(domain.getReceiver()),
        domain.getTimestamp()
    );
  }

}

package com.picpaydesafio.demopicpaydesafio.domain.transction.factory;

import com.picpaydesafio.demopicpaydesafio.application.user.service.UserService;
import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.user.model.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.transation.entity.TransactionEntity;
import com.picpaydesafio.demopicpaydesafio.infrastructure.user.mapper.UserMapper;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.transaction.dtos.TransactionResponseDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionFactoryImpl implements TransactionFactory {

  private final UserService userService;
  private final UserMapper userMapper;


  @Override
  public Transaction createDomain(TransactionRequestDTO request) {
    User sender = userService.findUserById(request.senderId());
    User receiver = userService.findUserById(request.receiverId());

    return new Transaction(null, request.amount(), sender, receiver, LocalDateTime.now());
  }

  @Override
  public Transaction createDomain(TransactionEntity entity) {
    return new Transaction(entity.getId(), entity.getAmount(), userMapper.toDomain(entity.getSender()),
        userMapper.toDomain(entity.getReceiver()), entity.getTimestamp()
    );
  }

  @Override
  public TransactionEntity createEntity(Transaction domain) {
    return new TransactionEntity(domain.getId(), domain.getAmount(), userMapper.toEntity(domain.getSender()),
        userMapper.toEntity(domain.getReceiver()), domain.getTimestamp()
    );
  }

  @Override
  public Transaction craeteDomain(
      Long id, BigDecimal amount, User sender, User receiver, LocalDateTime timestamp
  ) {
    return new Transaction(id, amount, sender, receiver, timestamp);
  }

  @Override
  public TransactionResponseDTO createDTO(Transaction transaction) {
    return new TransactionResponseDTO(
        transaction.getId(),
        transaction.getSender().getId(),
        transaction.getSender().fullName(),
        transaction.getReceiver().getId(),
        transaction.getReceiver().fullName(),
        transaction.getAmount(),
        transaction.getTimestamp());
  }

}

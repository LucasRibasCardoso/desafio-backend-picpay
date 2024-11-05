package com.picpaydesafio.demopicpaydesafio.infrastructure.transation.entity;

import com.picpaydesafio.demopicpaydesafio.infrastructure.user.entity.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity()
@Table(name = "tb_transactions")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private BigDecimal amount;

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private UserEntity sender;

  @ManyToOne
  @JoinColumn(name = "receiver_id")
  private UserEntity receiver;

  private LocalDateTime timestamp;

}
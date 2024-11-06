package com.picpaydesafio.demopicpaydesafio.web.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
    Long id,
    Long senderId,
    String senderName,
    Long receiverId,
    String receiverName,
    BigDecimal amount,
    LocalDateTime timestamp
) {}

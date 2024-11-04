package com.picpaydesafio.demopicpaydesafio.web.transaction.dtos;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;


public record TransactionRequestDTO(
    @NotNull(message = "O id do pagador precisa ser informado.")
    Long senderId,

    @NotNull(message = "O id do recebedor precisa ser informado.")
    Long receiverId,

    @NotNull(message = "O valor da trânsferencia precisa ser informado.")
    @Positive(message = "O valor da trânsferencia precisa ser maior que zero.")
    BigDecimal amount) {

}

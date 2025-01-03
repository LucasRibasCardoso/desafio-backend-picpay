package com.picpaydesafio.demopicpaydesafio.web.dtos;

import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import java.math.BigDecimal;

public record UserResponseDTO(
    String firstName,
    String lastName,
    String document,
    BigDecimal balance,
    String email,
    String password,
    UserType userType
){}

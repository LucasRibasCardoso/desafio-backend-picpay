package com.picpaydesafio.demopicpaydesafio.web.user.dtos;

import com.picpaydesafio.demopicpaydesafio.infrastructure.user.entity.enums.UserType;
import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String document, BigDecimal balance, String email,
                      String password, UserType userType) {}

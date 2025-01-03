package com.picpaydesafio.demopicpaydesafio.web.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

    @NotBlank(message = "O campo email é obrigatório")
    String email,

    @NotBlank(message = "O campo senha é obrigatório")
    String password) {
}

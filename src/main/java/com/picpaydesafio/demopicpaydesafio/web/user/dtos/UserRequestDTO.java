package com.picpaydesafio.demopicpaydesafio.web.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(

    @NotBlank(message = "É necessário informar o nome.")
    String firstName,
    @NotBlank(message = "É necessário informar o sobrenome.")
    String lastName,

    @NotNull(message = "É necessário informar o documento.")
    String document,

    @NotBlank(message = "É necessário informar o email.")
    @Email
    String email,

    @NotBlank(message = "É necessário informar a senha.")
    String password,

    @NotBlank(message = "É necessário informar o tipo de usuário.")
    String userType

) {}

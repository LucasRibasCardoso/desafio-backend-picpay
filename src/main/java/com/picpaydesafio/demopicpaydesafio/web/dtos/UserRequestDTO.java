package com.picpaydesafio.demopicpaydesafio.web.dtos;

import com.picpaydesafio.demopicpaydesafio.web.utils.CPFWithMask;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(

    @NotBlank(message = "O campo nome é obrigatório.")
    String firstName,

    @NotBlank(message = "O campo sobrenome é obrigatório.")
    String lastName,

    @NotNull(message = "O campo documento é obrigatório.")
    @CPFWithMask
    String document,

    @NotBlank(message = "O campo email é obrigatório.")
    @Email(message = "Email informado possuí um formato inválido.")
    String email,

    @NotBlank(message = "O campo senha é obrigatório.")
    String password,

    @NotBlank(message = "O campo tipo de usuário é obrigatório.")
    String userType

) {}

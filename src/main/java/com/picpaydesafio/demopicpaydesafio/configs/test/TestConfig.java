package com.picpaydesafio.demopicpaydesafio.configs.test;

import com.picpaydesafio.demopicpaydesafio.infrastructure.user.entity.UserEntity;
import com.picpaydesafio.demopicpaydesafio.infrastructure.user.entity.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.web.user.dtos.UserDTO;
import com.picpaydesafio.demopicpaydesafio.infrastructure.user.repository.UserJpaRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor()
public class TestConfig implements CommandLineRunner {

  private final UserJpaRepository repository;

  @Override
  public void run(String... args) {

    UserDTO user1 = new UserDTO(
        "João",
        "Silva",
        "12345678900",
        new BigDecimal("1000"),
        "lucas.ribas.card@gmail.com",
        "password123",
        UserType.COMMON
    );

    UserDTO user2 = new UserDTO(
        "Maria",
        "Santos",
        "98765432100",
        new BigDecimal("0"),
        "lucas.rib.card@gmail.com",
        "password456",
        UserType.COMMON
    );

    UserDTO user3 = new UserDTO(
        "Carlos",
        "Oliveira",
        "56789012345",
        new BigDecimal("200"),
        "carlos@example.com",
        "password789",
        UserType.MERCHANT
    );

    UserEntity userEntity1 = new UserEntity(user1);
    UserEntity userEntity2 = new UserEntity(user2);
    UserEntity userEntity3 = new UserEntity(user3);
    repository.saveAll(List.of(userEntity1, userEntity2, userEntity3));

  }

}

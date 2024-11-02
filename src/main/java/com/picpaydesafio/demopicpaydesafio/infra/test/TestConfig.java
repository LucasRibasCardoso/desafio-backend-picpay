package com.picpaydesafio.demopicpaydesafio.infra.test;

import com.picpaydesafio.demopicpaydesafio.domain.user.User;
import com.picpaydesafio.demopicpaydesafio.domain.user.UserType;
import com.picpaydesafio.demopicpaydesafio.dtos.UserDTO;
import com.picpaydesafio.demopicpaydesafio.repositories.TransactionRepository;
import com.picpaydesafio.demopicpaydesafio.repositories.UserRepository;
import com.picpaydesafio.demopicpaydesafio.services.UserService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig implements CommandLineRunner {

  private final UserRepository repository;
  private final TransactionRepository transactionRepository;
  private final UserService userService;

  @Autowired
  public TestConfig(UserRepository repository, TransactionRepository transactionRepository, UserService userService) {
    this.repository = repository;
    this.transactionRepository = transactionRepository;
    this.userService = userService;
  }

  @Override
  public void run(String... args) throws Exception {

    UserDTO user1 = new UserDTO(
        "João",
        "Silva",
        "12345678900",
        new BigDecimal("1000"),
        "joao@example.com",
        "password123",
        UserType.COMMON
    );

    UserDTO user2 = new UserDTO(
        "Maria",
        "Santos",
        "98765432100",
        new BigDecimal("0"),
        "maria@example.com",
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

    User userCreated1 = userService.createUser(user1);
    User userCreated2 = userService.createUser(user2);
    User userCreated3 = userService.createUser(user3);
    List<User> users = List.of(userCreated1, userCreated2, userCreated3);

    repository.saveAll(users);

  }

}

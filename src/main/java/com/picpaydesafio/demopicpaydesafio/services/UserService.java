package com.picpaydesafio.demopicpaydesafio.services;

import com.picpaydesafio.demopicpaydesafio.domain.user.User;
import com.picpaydesafio.demopicpaydesafio.domain.user.UserType;
import com.picpaydesafio.demopicpaydesafio.dtos.UserDTO;
import com.picpaydesafio.demopicpaydesafio.repositories.UserRepository;
import com.picpaydesafio.demopicpaydesafio.services.exceptions.InsufficientBalance;
import com.picpaydesafio.demopicpaydesafio.services.exceptions.UserAlreadyExists;
import com.picpaydesafio.demopicpaydesafio.services.exceptions.UserNotFound;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  public void validateTransaction(User sender, BigDecimal amount) {
    if (sender.getUserType() == UserType.MERCHANT) {
      throw new UserAlreadyExists("Usuário não está autorizado a realizar transação.");
    }

    if (sender.getBalance().compareTo(amount) < 0) {
      throw new InsufficientBalance("Saldo insuficiente.");
    }
  }

  public User findUserById(Long id) {
    return this.repository.findUserById(id).orElseThrow(() -> new UserNotFound("Usuário não encontrado"));
  }

  public void saveUser(User user) {
    this.repository.save(user);
  }

  public User createUser(UserDTO data) {
    User newUser = new User(data);
    this.saveUser(newUser);
    return newUser;
  }

  public List<User> getAllUsers() {
    return this.repository.findAll();
  }
}

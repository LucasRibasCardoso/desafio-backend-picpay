package com.picpaydesafio.demopicpaydesafio.services;

import com.picpaydesafio.demopicpaydesafio.domain.user.User;
import com.picpaydesafio.demopicpaydesafio.domain.user.UserType;
import com.picpaydesafio.demopicpaydesafio.repositories.UserRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  public void validateTrasaction(User sender, BigDecimal amount) throws Exception {
    if (sender.getUserType() == UserType.MERCHANT) {
      throw new Exception("Usuário não está autorizado a realizar transação.");
    }

    if (sender.getBalance().compareTo(amount) < 0) {
      throw new Exception("Saldo insuficiente.");
    }
  }

  public User findUserById(Long id) throws Exception {
    return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
  }

  public void saveUser(User user) throws Exception {
    this.repository.save(user);
  }
}

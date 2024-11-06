package com.picpaydesafio.demopicpaydesafio.domain.models;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.InsufficientFoundsException;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import java.math.BigDecimal;
import lombok.Value;


@Value
public class User {

  Long id;
  String firstName;
  String lastName;
  String document;
  String email;
  String password;
  BigDecimal balance;
  UserType userType;


  public String fullName() {
    return this.firstName + " " + this.lastName;
  }

  public boolean isMerchant() {
    return this.userType.equals(UserType.MERCHANT);
  }

  public User credit(BigDecimal amount) {
    return new User(id, firstName, lastName, document, email, password, balance.add(amount), userType);
  }

  public User debit(BigDecimal amount) {
    if (!hasSufficientBalance(amount)) {
      throw new InsufficientFoundsException("Saldo insuficiente para realizar a transação.");
    }
    return new User(id, firstName, lastName, document, email, password, balance.subtract(amount), userType);
  }

  private boolean hasSufficientBalance(BigDecimal amount) {
    return this.balance.compareTo(amount) >= 0;
  }
}

package com.picpaydesafio.demopicpaydesafio.domain.user.model;

import com.picpaydesafio.demopicpaydesafio.application.transaction.exceptions.InsufficientFoundsException;
import com.picpaydesafio.demopicpaydesafio.infrastructure.user.entity.enums.UserType;
import java.math.BigDecimal;
import lombok.Value;

/**
 * Represents a user in the system, with various properties such as name, email, balance, and user type.
 * Provides methods to credit and debit the user's balance, with validation to ensure sufficient funds.
 */
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
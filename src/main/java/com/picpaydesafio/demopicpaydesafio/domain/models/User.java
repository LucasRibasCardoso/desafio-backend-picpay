package com.picpaydesafio.demopicpaydesafio.domain.models;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.InsufficientFoundsException;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserRole;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Value
public class User implements UserDetails {

  Long id;
  String firstName;
  String lastName;
  String document;
  String email;
  String password;
  BigDecimal balance;
  UserType userType;
  UserRole role;

  public String fullName() {
    return this.firstName + " " + this.lastName;
  }

  public boolean isMerchant() {
    return this.userType.equals(UserType.MERCHANT);
  }

  public User credit(BigDecimal amount) {
    return new User(id, firstName, lastName, document, email, password, balance.add(amount), userType, role);
  }

  public User debit(BigDecimal amount) {
    if (!hasSufficientBalance(amount)) {
      throw new InsufficientFoundsException("Saldo insuficiente para realizar a transação.");
    }
    return new User(
        id, firstName, lastName, document, email, password, balance.subtract(amount), userType, role);
  }

  private boolean hasSufficientBalance(BigDecimal amount) {
    return this.balance.compareTo(amount) >= 0;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}

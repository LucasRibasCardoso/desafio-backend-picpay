package com.picpaydesafio.demopicpaydesafio.infrastructure.entities;

import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserRole;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity()
@Table(name = "tb_users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  @Column(unique = true)
  private String document;

  @Column(unique = true)
  private String email;

  private String password;

  private BigDecimal balance;

  @Enumerated(EnumType.STRING)
  private UserType userType;

  private UserRole role;

  public UserEntity(Long id, String firstName, String lastName, String document, String email,
      String password, BigDecimal balance, UserType userType) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.document = document;
    this.email = email;
    this.password = password;
    this.userType = userType;
    this.balance = balance;
  }



}

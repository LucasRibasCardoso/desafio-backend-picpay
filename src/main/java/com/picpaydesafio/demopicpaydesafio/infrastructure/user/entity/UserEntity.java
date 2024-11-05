package com.picpaydesafio.demopicpaydesafio.infrastructure.user.entity;

import com.picpaydesafio.demopicpaydesafio.infrastructure.user.entity.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.web.user.dtos.UserResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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


  // usado no teste config
  public UserEntity(UserResponseDTO data) {
    this.firstName = data.firstName();
    this.lastName = data.lastName();
    this.balance = data.balance();
    this.document = data.document();
    this.userType = data.userType();
    this.password = data.password();
    this.email = data.email();
  }

}

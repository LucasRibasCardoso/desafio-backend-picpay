package com.picpaydesafio.demopicpaydesafio.infrastructure.mappers;

import static org.junit.jupiter.api.Assertions.*;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.UserEntity;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserResponseDTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserMapperTest {

  /**
   * Utilizar essa classe como padrão para as outras
   */

  // User
  public static final long ID = 1L;
  public static final String FIRSTNAME = "teste";
  public static final String LASTNAME = "example";
  public static final String DOCUMENT = "12312312312";
  public static final String EMAIL = "example@gmail.com";
  public static final UserType USER_TYPE = UserType.COMMON;
  public static final String PASSWORD = "password";
  public static final BigDecimal BALANCE = new BigDecimal("100.00");

  private UserMapper userMapper;
  private UserEntity mockUserEntity;
  private User mockUserDomain;

  @BeforeEach
  void setUp() {
    userMapper = new UserMapper();
    mockUserEntity = new UserEntity(ID, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE);
    mockUserDomain = new User(ID, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE);
  }

  @Test
  void toDomain_ShouldReturnObjectUserDomain() {
    // Act
    User result = userMapper.toDomain(mockUserEntity);

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(ID, result.getId()),
        () -> assertEquals(FIRSTNAME, result.getFirstName()),
        () -> assertEquals(LASTNAME, result.getLastName()),
        () -> assertEquals(DOCUMENT, result.getDocument()), () -> assertEquals(EMAIL, result.getEmail()),
        () -> assertEquals(PASSWORD, result.getPassword()), () -> assertEquals(BALANCE, result.getBalance()),
        () -> assertEquals(USER_TYPE, result.getUserType())
    );
  }

  @Test
  void toEntity_ShouldReturnObjectUserEntity() {
    // Act
    UserEntity result = userMapper.toEntity(mockUserDomain);

    // Assert
    assertAll (
        () -> assertNotNull(result),
        () -> assertEquals(ID, result.getId()),
        () -> assertEquals(FIRSTNAME, result.getFirstName()),
        () -> assertEquals(LASTNAME, result.getLastName()),
        () -> assertEquals(DOCUMENT, result.getDocument()), () -> assertEquals(EMAIL, result.getEmail()),
        () -> assertEquals(PASSWORD, result.getPassword()), () -> assertEquals(BALANCE, result.getBalance()),
        () -> assertEquals(USER_TYPE, result.getUserType())
    );
  }

  @Test
  void toResponseDTO_ShouldReturnObjectUserResponseDTO() {
    // Act
    UserResponseDTO result = userMapper.toResponseDTO(mockUserDomain);

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(FIRSTNAME, result.firstName()),
        () -> assertEquals(LASTNAME, result.lastName()), () -> assertEquals(DOCUMENT, result.document()),
        () -> assertEquals(EMAIL, result.email()), () -> assertEquals(PASSWORD, result.password()),
        () -> assertEquals(BALANCE, result.balance()), () -> assertEquals(USER_TYPE, result.userType())
    );
  }

}


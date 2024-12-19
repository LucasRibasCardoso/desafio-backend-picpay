package com.picpaydesafio.demopicpaydesafio.domain.factories.imp;

import static org.junit.jupiter.api.Assertions.*;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserRequestDTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserFactoryImpTest {

  // UserDTO
  public static final String FIRSTNAME = "teste";
  public static final String LASTNAME = "example";
  public static final String DOCUMENT = "12312312312";
  public static final String EMAIL = "example@gmail.com";
  public static final BigDecimal BALANCE = new BigDecimal("100.00");
  public static final String USER_TYPE = "COMMON";
  public static final String PASSWORD = "password";
  public static final String ENCRYPTED_PASSWORD = "ngfjkanleiagahrfgblaiwbreligbierfg";
  public static final UserType USER_TYPE_OBJECT = UserType.COMMON;
  public static final BigDecimal AMOUNT = BigDecimal.ZERO;
  public static final String USER_ROLE = "USER";


  private UserFactoryImp userFactory;

  private UserRequestDTO mockUserRequestDTO;

  @BeforeEach
  void setUp() {
    userFactory = new UserFactoryImp();
  }

  @Test
  void createDomain_ShouldCreateObjectUserWithEncryptedPassword() {
    // Act
    mockUserRequestDTO = new UserRequestDTO(
        FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, BALANCE, PASSWORD, USER_TYPE, USER_ROLE);
    User user = userFactory.createDomain(mockUserRequestDTO, ENCRYPTED_PASSWORD);

    // Arrange
    assertAll(() -> assertNotNull(user),
        () -> assertEquals(mockUserRequestDTO.firstName(), user.getFirstName()),
        () -> assertEquals(mockUserRequestDTO.lastName(), user.getLastName()),
        () -> assertEquals(mockUserRequestDTO.document(), user.getDocument()),
        () -> assertEquals(mockUserRequestDTO.email(), user.getEmail()),
        () -> assertEquals(ENCRYPTED_PASSWORD, user.getPassword()),
        () -> assertEquals(BALANCE, user.getBalance()),
        () -> assertEquals(USER_TYPE_OBJECT, user.getUserType())
    );
  }

}
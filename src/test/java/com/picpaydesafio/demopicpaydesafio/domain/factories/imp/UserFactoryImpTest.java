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
  public static final String USER_TYPE = "COMMON";
  public static final String PASSWORD = "password";
  public static final UserType USER_TYPE_OBJECT = UserType.COMMON;
  public static final BigDecimal AMOUNT = BigDecimal.ZERO;

  private UserFactoryImp userFactory;
  
  private UserRequestDTO mockUserRequestDTO;

  @BeforeEach
  void setUp() {
    userFactory = new UserFactoryImp();
    mockUserRequestDTO = new UserRequestDTO(FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, USER_TYPE);
  }

  @Test
  void createDomain_ShouldCreateObjectUser() {
    // Act
    User user = userFactory.createDomain(mockUserRequestDTO);

    // Arrange
    assertAll(
        () -> assertNotNull(user),
        () -> assertEquals(mockUserRequestDTO.firstName(), user.getFirstName()),
        () -> assertEquals(mockUserRequestDTO.lastName(), user.getLastName()),
        () -> assertEquals(mockUserRequestDTO.document(), user.getDocument()),
        () -> assertEquals(mockUserRequestDTO.email(), user.getEmail()),
        () -> assertEquals(mockUserRequestDTO.password(), user.getPassword()),
        () -> assertEquals(AMOUNT, user.getBalance()),
        () -> assertEquals(USER_TYPE_OBJECT, user.getUserType())
    );
  }

}
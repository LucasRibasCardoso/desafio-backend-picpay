package com.picpaydesafio.demopicpaydesafio.domain.factories.imp;

import static org.junit.jupiter.api.Assertions.*;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserRequestDTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserFactoryImpTest {

  public static final String FIRST_NAME = "Maria";
  public static final String LAST_NAME = "Santos";
  public static final String DOCUMENT = "12312312312";
  public static final String EMAIL = "maria@example.com";
  public static final String PASSWORD = "123";
  public static final String USER_TYPE = "COMMON";

  private UserFactoryImp userFactory;

  @BeforeEach
  void setUp() {
    userFactory = new UserFactoryImp();
  }

  @Test
  void createDomain_ShouldCreateObjectUser() {
    // Arrange
    UserRequestDTO userRequest = new UserRequestDTO(
        FIRST_NAME, LAST_NAME, DOCUMENT, EMAIL, PASSWORD, USER_TYPE
    );

    // Act
    User user = userFactory.createDomain(userRequest);

    // Arrange
    assertNotNull(user);
    assertEquals(userRequest.firstName(), user.getFirstName());
    assertEquals(userRequest.lastName(), user.getLastName());
    assertEquals(userRequest.document(), user.getDocument());
    assertEquals(userRequest.email(), user.getEmail());
    assertEquals(userRequest.password(), user.getPassword());
    assertEquals(BigDecimal.ZERO, user.getBalance());
    assertEquals(UserType.COMMON, user.getUserType());
  }

}
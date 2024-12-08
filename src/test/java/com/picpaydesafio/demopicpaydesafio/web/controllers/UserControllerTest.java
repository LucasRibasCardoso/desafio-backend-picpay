package com.picpaydesafio.demopicpaydesafio.web.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picpaydesafio.demopicpaydesafio.application.services.UserService;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserResponseDTO;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  // User
  public static final long ID = 1L;
  public static final String FIRSTNAME = "teste";
  public static final String LASTNAME = "example";
  public static final String DOCUMENT = "12312312312";
  public static final String EMAIL = "example@gmail.com";
  public static final UserType USER_TYPE = UserType.COMMON;
  public static final String PASSWORD = "password";
  public static final BigDecimal BALANCE = new BigDecimal("100.00");
  public static final String USER_ROLE = "USER";

  @InjectMocks
  private UserController userController;

  @Mock
  private UserService userService;

  private UserResponseDTO mockUserResponse;

  @BeforeEach
  void setUp() {
    mockUserResponse = new UserResponseDTO(FIRSTNAME, LASTNAME, DOCUMENT, BALANCE, EMAIL, PASSWORD, USER_TYPE);
  }


  @Test
  void getAllUsers_ShouldReturnListOfUserResponseDTO_WhenUsersExist() {
    // Arrange
    when(userService.findAllUsers()).thenReturn(List.of(mockUserResponse));

    // Act
    ResponseEntity<List<UserResponseDTO>> result = userController.getAllUsers();

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(1, result.getBody().size()),
        () -> assertInstanceOf(UserResponseDTO.class, result.getBody().get(0)),
        () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
        () -> assertEquals(mockUserResponse.firstName(), result.getBody().get(0).firstName()),
        () -> assertEquals(mockUserResponse.lastName(), result.getBody().get(0).lastName()),
        () -> assertEquals(mockUserResponse.document(), result.getBody().get(0).document()),
        () -> assertEquals(mockUserResponse.balance(), result.getBody().get(0).balance()),
        () -> assertEquals(mockUserResponse.email(), result.getBody().get(0).email()),
        () -> assertEquals(mockUserResponse.password(), result.getBody().get(0).password()),
        () -> assertEquals(mockUserResponse.userType(), result.getBody().get(0).userType())
    );
    verify(userService).findAllUsers();
  }

  @Test
  void getAllUsers_ShouldReturnEmptyList_WhenNoUsersExist(){
    // Arrange
    when(userService.findAllUsers()).thenReturn(Collections.emptyList());

    // Act
    ResponseEntity<List<UserResponseDTO>> result = userController.getAllUsers();

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertTrue(result.getBody().isEmpty()),
        () -> assertInstanceOf(List.class, result.getBody()),
        () -> assertEquals(HttpStatus.OK, result.getStatusCode())
    );
  }
}
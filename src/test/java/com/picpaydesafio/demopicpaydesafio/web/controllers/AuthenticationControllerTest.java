package com.picpaydesafio.demopicpaydesafio.web.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.web.dtos.UserRequestDTO;
import java.math.BigDecimal;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picpaydesafio.demopicpaydesafio.application.services.AuthenticationService;
import com.picpaydesafio.demopicpaydesafio.web.dtos.LoginRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.LoginResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

  public static final String USUARIO_NAO_ENCONTRADO_COM_O_EMAIL_INVALIDUSERNAME =
      "Usuário não encontrado com o email: invalidusername";

  // UserDTO
  public static final String FIRSTNAME = "teste";
  public static final String LASTNAME = "example";
  public static final String DOCUMENT = "12312312312";
  public static final String EMAIL = "example@gmail.com";
  public static final BigDecimal BALANCE = new BigDecimal("100.00");
  public static final String USER_TYPE = "COMMON";
  public static final String PASSWORD = "password";
  public static final String USER_ROLE = "USER";

  @InjectMocks
  private AuthenticationController authController;

  @Mock
  private AuthenticationService authenticationService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void login_Success() {
    // Arrange
    LoginRequestDTO authDTO = new LoginRequestDTO("username", "password");
    LoginResponseDTO expectedResponse = new LoginResponseDTO("jwt-token");

    when(authenticationService.login(authDTO)).thenReturn(expectedResponse);

    // Act
    ResponseEntity<LoginResponseDTO> response = authController.login(authDTO);

    // Assert
    assertNotNull(response);
    assertEquals(
        200, response.getStatusCode()
            .value());
    assertEquals(expectedResponse, response.getBody());

    verify(authenticationService).login(authDTO);
  }

  @Test
  void login_ShouldThrowException_WhenUserNotFound() {
    // Arrange
    LoginRequestDTO authDTO = new LoginRequestDTO("invalidusername", "invalidpassword");

    when(authenticationService.login(authDTO)).thenThrow(
        new RuntimeException(USUARIO_NAO_ENCONTRADO_COM_O_EMAIL_INVALIDUSERNAME));

    // Act
    RuntimeException exception = assertThrows(RuntimeException.class, () -> authController.login(authDTO));

    // Assert
    assertAll(() -> assertInstanceOf(RuntimeException.class, exception),
        () -> assertEquals(USUARIO_NAO_ENCONTRADO_COM_O_EMAIL_INVALIDUSERNAME, exception.getMessage())
    );
  }

  @Test
  void login_ShouldThrowException_WhenInvalidCredentials() throws Exception {
    // Arrange
    LoginRequestDTO authDTO = new LoginRequestDTO("invalidusername", "");

    String invalidRequestJson = objectMapper.writeValueAsString(authDTO);

    // Act & Assert
    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidRequestJson))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Erro de validação"))
        .andExpect(jsonPath("$.statusCode").value(400))
        .andExpect(jsonPath("$.fieldErrors.password").value("O campo senha é obrigatório"));
  }

  @Test
  void register_Success() {
    // Arrange
    UserRequestDTO mockUserDTO = new UserRequestDTO(
        FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, BALANCE, USER_TYPE, PASSWORD, USER_ROLE);

    // Act
    assertDoesNotThrow(() -> authController.register(mockUserDTO));
    verify(authenticationService, times(1)).register(mockUserDTO);
  }

  @Test
  void register_ShouldThrowException_WhenInvalidCredentials() throws Exception {

    UserRequestDTO invalidUserDTO = new UserRequestDTO(
        "", LASTNAME, "", EMAIL, BALANCE, "" ,USER_TYPE, USER_ROLE);

    String invalidRequestJson = objectMapper.writeValueAsString(invalidUserDTO);

    // Act & Assert
    mockMvc.perform(post("/auth/register")
            .content(invalidRequestJson)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Erro de validação"))
        .andExpect(jsonPath("$.statusCode").value(400))
        .andExpect(jsonPath("$.fieldErrors.firstName").value("O campo nome é obrigatório."))
        .andExpect(jsonPath("$.fieldErrors.document").value("O campo documento é obrigatório."))
        .andExpect(jsonPath("$.fieldErrors.password").value("O campo senha é obrigatório."));
  }
}
package com.picpaydesafio.demopicpaydesafio.application.services.imp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserAlreadyExistsException;
import com.picpaydesafio.demopicpaydesafio.domain.factories.UserFactory;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesDomain.UserRepository;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserRole;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.web.dtos.LoginRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.LoginResponseDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserRequestDTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImpTest {

  // User
  public static final long ID = 1L;
  public static final String FIRSTNAME = "teste";
  public static final String LASTNAME = "example";
  public static final String DOCUMENT = "12312312312";
  public static final String EMAIL = "example@gmail.com";
  public static final BigDecimal BALANCE = new BigDecimal("100.00");
  public static final String USER_TYPE = "COMMON";
  public static final String PASSWORD = "password";
  public static final String USER_ROLE = "USER";

  public static final String FAKE_JWT_TOKEN = "fake-jwt-token";
  public static final String ENCRYPTED_PASSWORD = "encryptedPassword";


  @InjectMocks
  private AuthenticationServiceImp authenticationService;

  @Mock
  private UserFactory userFactory;

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private TokenServiceImp tokenService;

  @Mock
  private Authentication authentication;

  private LoginRequestDTO mockLoginRequestDTO;
  private UserRequestDTO mockUserRequestDTO;
  private User mockUser;

  @BeforeEach
  void setUp() {
    authentication = mock(Authentication.class);
    mockLoginRequestDTO = new LoginRequestDTO(EMAIL, PASSWORD);
    mockUserRequestDTO = new UserRequestDTO(
        FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, BALANCE, PASSWORD, USER_TYPE, USER_ROLE);
    mockUser = new User(
        ID, FIRSTNAME, LASTNAME, DOCUMENT, PASSWORD, EMAIL, BALANCE, UserType.COMMON, UserRole.USER);
  }

  @Test
  void loginSuccess() {
    // Arrange
    UsernamePasswordAuthenticationToken dataLogin = new UsernamePasswordAuthenticationToken(
        mockLoginRequestDTO.email(), mockLoginRequestDTO.password());

    when(authenticationManager.authenticate(dataLogin)).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(mockUser);
    when(tokenService.generateToken(mockUser.getEmail(), mockUser.getRole())).thenReturn(FAKE_JWT_TOKEN);

    // Act
    LoginResponseDTO response = authenticationService.login(mockLoginRequestDTO);

    // Assert
    assertNotNull(response);
    assertEquals(FAKE_JWT_TOKEN, response.token());
    verify(authenticationManager).authenticate(dataLogin);
    verify(tokenService).generateToken(mockUser.getEmail(), mockUser.getRole());
  }

  @Test
  void register_ShouldThrowException_WhenUserAlreadyExists() {
    // Arrange
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));

    // Act & Assert
    UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
      authenticationService.register(mockUserRequestDTO);
    });

    assertEquals("Esse email já está em uso. Tente utilizar outro email.", exception.getMessage());

    verify(userRepository).findByEmail(mockUserRequestDTO.email());
    verifyNoInteractions(userFactory, passwordEncoder, tokenService);
  }

  @Test
  void registerSuccess() {
    // Arrange
    when(userRepository.findByEmail(mockUserRequestDTO.email())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(mockUserRequestDTO.password())).thenReturn(ENCRYPTED_PASSWORD);
    when(userFactory.createDomain(mockUserRequestDTO, ENCRYPTED_PASSWORD)).thenReturn(mockUser);
    when(userRepository.save(mockUser)).thenReturn(mockUser);

    // Act
    authenticationService.register(mockUserRequestDTO);

    // Assert
    verify(userRepository).findByEmail(mockUserRequestDTO.email());
    verify(passwordEncoder).encode(mockUserRequestDTO.password());
    verify(userFactory).createDomain(mockUserRequestDTO, ENCRYPTED_PASSWORD);
    verify(userRepository).save(mockUser);
  }

}
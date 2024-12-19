package com.picpaydesafio.demopicpaydesafio.application.services.imp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.application.exceptions.InvalidEmailException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserAlreadyExistsException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserNotFoundException;
import com.picpaydesafio.demopicpaydesafio.application.usecases.ValidateUserUseCase;
import com.picpaydesafio.demopicpaydesafio.domain.factories.UserFactory;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesDomain.UserRepository;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserRole;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.infrastructure.mappers.UserMapper;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserResponseDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

  // User Sender
  public static final long ID_1 = 1L;
  public static final String FIRSTNAME_1 = "teste1";
  public static final String LASTNAME_1 = "example1";
  public static final String DOCUMENT_1 = "12312312312";
  public static final String EMAIL_1 = "example1@gmail.com";
  public static final UserType USER_TYPE_1 = UserType.COMMON;
  public static final String PASSWORD_1 = "password";
  public static final BigDecimal BALANCE_1 = new BigDecimal("100.00");

  // User Receiver
  public static final long ID_2 = 2L;
  public static final String FIRSTNAME_2 = "teste2";
  public static final String LASTNAME_2 = "example2";
  public static final String DOCUMENT_2 = "12312312312";
  public static final String EMAIL_2 = "example2@gmail.com";
  public static final UserType USER_TYPE_2 = UserType.COMMON;
  public static final String PASSWORD_2 = "password";
  public static final BigDecimal BALANCE_2 = new BigDecimal("100.00");

  public static final UserRole USER_ROLE = UserRole.USER;

  public static final long ID_3 = 3L;


  @InjectMocks
  private UserServiceImp userServiceImp;

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;


  private UserResponseDTO mockUserResponse;
  private User mockUser1;
  private User mockUser2;

  @BeforeEach
  void setUp() {
    mockUserResponse = new UserResponseDTO(
        FIRSTNAME_1, LASTNAME_1, DOCUMENT_1, BALANCE_1, EMAIL_1, PASSWORD_1, USER_TYPE_1
    );
    mockUser1 = new User(
        ID_1, FIRSTNAME_1, LASTNAME_1, DOCUMENT_1, EMAIL_1, PASSWORD_1, BALANCE_1, USER_TYPE_1, USER_ROLE
    );
    mockUser2 = new User(
        ID_2, FIRSTNAME_2, LASTNAME_2, DOCUMENT_2, EMAIL_2, PASSWORD_2, BALANCE_2, USER_TYPE_2, USER_ROLE
    );
  }

  @Test
  void findAllUsers_shouldReturnListOfUsers_whenUsersExist() {
    // Arrange
    when(userRepository.findAll()).thenReturn(List.of(mockUser1));
    when(userMapper.toResponseDTO(mockUser1)).thenReturn(mockUserResponse);

    // Act
    List<UserResponseDTO> users = userServiceImp.findAllUsers();

    // Assert
    assertAll(
        () -> assertInstanceOf(List.class, users),
        () -> assertEquals(1, users.size()),
        () -> assertInstanceOf(UserResponseDTO.class, users.get(0)),
        () -> assertEquals(mockUser1.getFirstName(), users.get(0).firstName()),
        () -> assertEquals(mockUser1.getLastName(), users.get(0).lastName()),
        () -> assertEquals(mockUser1.getDocument(), users.get(0).document()),
        () -> assertEquals(mockUser1.getBalance(), users.get(0).balance()),
        () -> assertEquals(mockUser1.getEmail(), users.get(0).email()),
        () -> assertEquals(mockUser1.getPassword(), users.get(0).password()),
        () -> assertEquals(mockUser1.getUserType(), users.get(0).userType())
    );

    verify(userRepository).findAll();
    verify(userMapper).toResponseDTO(mockUser1);

  }

  @Test
  void findAllUsers_shouldReturnEmptyList_whenNoUsersExist() {
    // Arrange
    when(userRepository.findAll()).thenReturn(List.of());

    // Act
    List<UserResponseDTO> users = userServiceImp.findAllUsers();

    // Assert
    assertAll(
        () -> assertInstanceOf(List.class, users),
        () -> assertTrue(users.isEmpty())
    );

    verify(userRepository).findAll();
    verify(userMapper, never()).toResponseDTO(any());
  }

  @Test
  void findUserById_ShouldReturnUser_whenUserExists() {
    // Arrange
    when(userRepository.findById(ID_1)).thenReturn(Optional.of(mockUser1));

    // Act
    User user = userServiceImp.findUserById(ID_1);

    // Assert
    assertAll(
        () -> assertNotNull(user),
        () -> assertInstanceOf(User.class, user),
        () -> assertEquals(mockUser1.getId(), user.getId()),
        () -> assertEquals(mockUser1.getFirstName(), user.getFirstName()),
        () -> assertEquals(mockUser1.getLastName(), user.getLastName()),
        () -> assertEquals(mockUser1.getDocument(), user.getDocument()),
        () -> assertEquals(mockUser1.getEmail(), user.getEmail()),
        () -> assertEquals(mockUser1.getPassword(), user.getPassword()),
        () -> assertEquals(mockUser1.getUserType(), user.getUserType()),
        () -> assertEquals(mockUser1.getBalance(), user.getBalance())
    );

    verify(userRepository).findById(ID_1);
  }

  @Test
  void findUserById_shouldThrowException_whenUserDoesNotExist() {
    // Arrange
    when(userRepository.findById(ID_3)).thenThrow(
        new UserNotFoundException("Usuário com id " + ID_3 + " não encontrado."));

    // Act & Assert
    UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userServiceImp.findUserById(ID_3));
    assertEquals("Usuário com id " + ID_3 + " não encontrado.", exception.getMessage());

    verify(userRepository).findById(ID_3);
  }

  @Test
  void saveUsersWithNewBalances_shouldSaveSenderAndReceiver() {
    // Act
    userServiceImp.saveUsersWithNewBalances(mockUser1, mockUser2);

    // Assert
    verify(userRepository).save(mockUser1);
    verify(userRepository).save(mockUser2);
  }

  @Test
  void findUserByEmail_ShouldReturnUser_WhenUserExists() {
    // Arrange
    when(userRepository.findByEmail(EMAIL_1)).thenReturn(Optional.of(mockUser1));

    // Act
    User user = userServiceImp.findUserByEmail(EMAIL_1);

    // Assert
    assertAll(
        () -> assertNotNull(user),
        () -> assertInstanceOf(User.class, user),
        () -> assertEquals(mockUser1.getId(), user.getId()),
        () -> assertEquals(mockUser1.getFirstName(), user.getFirstName()),
        () -> assertEquals(mockUser1.getLastName(), user.getLastName()),
        () -> assertEquals(mockUser1.getDocument(), user.getDocument()),
        () -> assertEquals(mockUser1.getEmail(), user.getEmail()),
        () -> assertEquals(mockUser1.getPassword(), user.getPassword()),
        () -> assertEquals(mockUser1.getUserType(), user.getUserType()),
        () -> assertEquals(mockUser1.getBalance(), user.getBalance())
    );
  }

}
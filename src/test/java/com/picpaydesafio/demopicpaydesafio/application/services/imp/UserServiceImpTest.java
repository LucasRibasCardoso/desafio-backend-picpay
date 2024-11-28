package com.picpaydesafio.demopicpaydesafio.application.services.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
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
import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserAlreadyExists;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.UserNotFound;
import com.picpaydesafio.demopicpaydesafio.application.usecases.ValidateUserUseCase;
import com.picpaydesafio.demopicpaydesafio.domain.factories.UserFactory;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesDomain.UserRepository;
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

  private static final long ID = 1L;
  private static final String JOAO = "João";
  private static final String CARVALHO = "Carvalho";
  private static final String DOCUMENT = "98515432130";
  private static final String EMAIL = "joao@gmail.com";
  private static final String PASSWORD = "joao123";
  private static final BigDecimal BALANCE = BigDecimal.valueOf(500);

  private static final String MARIA = "Maria";
  private static final String SILVA = "Silva";
  private static final String DOCUMENT_MARIA = "98765432130";
  private static final String EMAIL_MARIA = "maria@gmail.com";
  private static final String PASSWORD_MARIA = "maria123";
  private static final long ID_MARIA = 2L;

  private static final UserType USER_TYPE = UserType.COMMON;
  private static final String USER_TYPE_STRING = "COMMON";

  public static final String MESSAGE_ERROR_EMAIL_ALREADY_EXISTS = "O e-mail informado já está cadastrado. "
                                                                  + "Tente utilizar outro e-mail.";
  public static final String MESSAGE_ERROR_DOCUMENT_ALREADY_EXISTS = "O documento informado já está "
                                                                     + "cadastrado. Tente utilizar outro "
                                                                     + "documento.";
  public static final String MESSAGE_ERROR_INVALID_EMAIL = "O e-mail informado é inválido. Tente utilizar "
                                                           + "outro e-mail.";


  private UserRequestDTO userRequestDTO;
  private UserResponseDTO userResponseDTO;
  private User user1;
  private User user2;


  @InjectMocks
  private UserServiceImp userServiceImp;

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserFactory userFactory;

  @Mock
  private UserMapper userMapper;

  @Mock
  private ValidateUserUseCase validateUserUseCase;

  @BeforeEach
  void setUp() {
    initializeTestObjects();
  }

  @Test
  void findAllUsers_shouldReturnListOfUsers_whenUsersExist() {
    // Arrange
    when(userRepository.findAll()).thenReturn(List.of(user1));
    when(userMapper.toResponseDTO(user1)).thenReturn(userResponseDTO);

    // act
    List<UserResponseDTO> users = userServiceImp.findAllUsers();

    // assert
    assertInstanceOf(List.class, users);
    assertEquals(1, users.size());
    assertInstanceOf(UserResponseDTO.class, users.get(0));

    assertEquals(
        user1.getFirstName(), users.get(0)
            .firstName());
    assertEquals(
        user1.getLastName(), users.get(0)
            .lastName());
    assertEquals(
        user1.getDocument(), users.get(0)
            .document());
    assertEquals(
        user1.getBalance(), users.get(0)
            .balance());
    assertEquals(
        user1.getEmail(), users.get(0)
            .email());
    assertEquals(
        user1.getPassword(), users.get(0)
            .password());
    assertEquals(
        user1.getUserType(), users.get(0)
            .userType());

    verify(userRepository).findAll();
    verify(userMapper).toResponseDTO(user1);

  }

  @Test
  void findAllUsers_shouldReturnEmptyList_whenNoUsersExist() {
    // Arrange
    when(userRepository.findAll()).thenReturn(List.of());

    // act
    List<UserResponseDTO> users = userServiceImp.findAllUsers();

    // assert
    assertInstanceOf(List.class, users);
    assertTrue(users.isEmpty());

    verify(userRepository).findAll();
    verify(userMapper, never()).toResponseDTO(any());
  }

  @Test
  void findUserById_ShouldReturnUser_whenUserExists() {
    // Arrange
    when(userRepository.findById(ID)).thenReturn(Optional.of(user1));

    // act
    User user = userServiceImp.findUserById(ID);

    // assert
    assertInstanceOf(User.class, user);

    assertEquals(user1.getId(), user.getId());
    assertEquals(user1.getFirstName(), user.getFirstName());
    assertEquals(user1.getLastName(), user.getLastName());
    assertEquals(user1.getDocument(), user.getDocument());
    assertEquals(user1.getEmail(), user.getEmail());
    assertEquals(user1.getPassword(), user.getPassword());
    assertEquals(user1.getUserType(), user.getUserType());
    assertEquals(user1.getBalance(), user.getBalance());

    verify(userRepository).findById(ID);
  }

  @Test
  void findUserById_shouldThrowException_whenUserDoesNotExist() {
    // Arrange
    when(userRepository.findById(ID)).thenThrow(
        new UserNotFound("Usuário com id " + ID + " não encontrado."));

    // act
    UserNotFound exception = assertThrows(UserNotFound.class, () -> userServiceImp.findUserById(ID));

    // assert
    assertEquals("Usuário com id " + ID + " não encontrado.", exception.getMessage());

    verify(userRepository).findById(ID);

  }

  @Test
  void saveUsersWithNewBalances_shouldSaveSenderAndReceiver() {
    // act
    userServiceImp.saveUsersWithNewBalances(user1, user2);

    // assert
    verify(userRepository).save(user1);
    verify(userRepository).save(user2);
  }

  @Test
  void saveNewUser_shouldSaveUser() {
    // arrange
    when(userFactory.createDomain(userRequestDTO)).thenReturn(user1);
    doNothing().when(validateUserUseCase)
        .execute(user1);
    when(userRepository.save(user1)).thenReturn(user1);
    when(userMapper.toResponseDTO(user1)).thenReturn(userResponseDTO);

    // act
    UserResponseDTO savedUser = userServiceImp.saveNewUser(userRequestDTO);

    // assert
    assertInstanceOf(UserResponseDTO.class, savedUser);

    verify(userFactory).createDomain(userRequestDTO);
    verify(validateUserUseCase).execute(user1);
    verify(userRepository).save(user1);
    verify(userMapper).toResponseDTO(user1);
  }

  @Test
  void saveNewUser_shouldThrowInvalidEmailException_whenEmailAlreadyExists() {
    // Arrange
    when(userFactory.createDomain(userRequestDTO)).thenReturn(user1);
    doThrow(new InvalidEmailException(MESSAGE_ERROR_EMAIL_ALREADY_EXISTS)).when(validateUserUseCase)
        .execute(user1);

    // act
    InvalidEmailException exception = assertThrows(InvalidEmailException.class,
        () -> userServiceImp.saveNewUser(userRequestDTO)
    );

    // assert
    assertInstanceOf(InvalidEmailException.class, exception);
    assertEquals(MESSAGE_ERROR_EMAIL_ALREADY_EXISTS, exception.getMessage());

    verify(userFactory).createDomain(userRequestDTO);
    verify(validateUserUseCase).execute(user1);
    verifyNoInteractions(userRepository, userMapper);
  }

  @Test
  void saveNewUser_shouldThrowUserAlreadyExists_whenDocumentAlreadyExists() {
    // Arrange
    when(userFactory.createDomain(userRequestDTO)).thenReturn(user1);
    doThrow(new UserAlreadyExists(MESSAGE_ERROR_DOCUMENT_ALREADY_EXISTS)).when(validateUserUseCase)
        .execute(user1);

    // act
    UserAlreadyExists exception = assertThrows(UserAlreadyExists.class,
        () -> userServiceImp.saveNewUser(userRequestDTO)
    );

    // assert
    assertInstanceOf(UserAlreadyExists.class, exception);
    assertEquals(MESSAGE_ERROR_DOCUMENT_ALREADY_EXISTS, exception.getMessage());

    verify(userFactory).createDomain(userRequestDTO);
    verify(validateUserUseCase).execute(user1);
    verifyNoInteractions(userRepository, userMapper);
  }

  @Test
  void saveNewUser_shouldThrowInvalidEmailException_whenEmailIsInvalid() {
    // Arrange
    when(userFactory.createDomain(userRequestDTO)).thenReturn(user1);
    doThrow(new InvalidEmailException(MESSAGE_ERROR_INVALID_EMAIL)).when(validateUserUseCase)
        .execute(user1);

    // act
    InvalidEmailException exception = assertThrows(InvalidEmailException.class,
        () -> userServiceImp.saveNewUser(userRequestDTO)
    );

    // assert
    assertInstanceOf(InvalidEmailException.class, exception);
    assertEquals(MESSAGE_ERROR_INVALID_EMAIL, exception.getMessage());

    verify(userFactory).createDomain(userRequestDTO);
    verify(validateUserUseCase).execute(user1);
    verifyNoInteractions(userRepository, userMapper);
  }

  private void initializeTestObjects() {
    userRequestDTO = new UserRequestDTO(JOAO, CARVALHO, DOCUMENT, EMAIL, PASSWORD, USER_TYPE_STRING);

    userResponseDTO = new UserResponseDTO(JOAO, CARVALHO, DOCUMENT, BALANCE, EMAIL, PASSWORD, USER_TYPE);

    user1 = new User(ID, JOAO, CARVALHO, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE);

    user2 = new User(ID_MARIA, MARIA, SILVA, DOCUMENT_MARIA, EMAIL_MARIA, PASSWORD_MARIA, BALANCE, USER_TYPE);
  }

}
package com.picpaydesafio.demopicpaydesafio.application.services.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.application.services.UserService;
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
  private static final String USER_TYPE_STRING = "COMMON";
  private static final UserType USER_TYPE = UserType.COMMON;

  private UserRequestDTO userRequestDTO;
  private UserResponseDTO userResponseDTO;
  private User user;


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
    when(userRepository.findAll()).thenReturn(List.of(user));
    when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

    // act
    List<UserResponseDTO> users = userServiceImp.findAllUsers();

    // assert
    assertInstanceOf(List.class, users);
    assertEquals(1, users.size());
    assertInstanceOf(UserResponseDTO.class, users.get(0));

    assertEquals(JOAO, users.get(0).firstName());
    assertEquals(CARVALHO, users.get(0).lastName());
    assertEquals(DOCUMENT, users.get(0).document());
    assertEquals(BALANCE, users.get(0).balance());
    assertEquals(EMAIL, users.get(0).email());
    assertEquals(PASSWORD, users.get(0).password());
    assertEquals(USER_TYPE, users.get(0).userType());

    verify(userRepository).findAll();
    verify(userMapper).toResponseDTO(user);

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
  void findUserById() {
  }

  @Test
  void saveUsersWithNewBalances() {
  }

  @Test
  void saveNewUser() {
  }

  private void initializeTestObjects() {
    userRequestDTO = new UserRequestDTO(
        JOAO, CARVALHO, DOCUMENT, EMAIL, PASSWORD, USER_TYPE_STRING
    );

    userResponseDTO = new UserResponseDTO(
        JOAO, CARVALHO, DOCUMENT, BALANCE, EMAIL, PASSWORD, USER_TYPE
    );

    user = new User(
        ID, JOAO, CARVALHO, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE
    );
  }
}
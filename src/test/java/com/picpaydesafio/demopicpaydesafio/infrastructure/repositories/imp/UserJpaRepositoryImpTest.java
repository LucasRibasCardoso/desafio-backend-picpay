package com.picpaydesafio.demopicpaydesafio.infrastructure.repositories.imp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.UserEntity;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserRole;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.infrastructure.mappers.UserMapper;
import com.picpaydesafio.demopicpaydesafio.infrastructure.repositories.UserJpaRepository;
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
class UserJpaRepositoryImpTest {

  // User
  public static final long ID = 1L;
  public static final String FIRSTNAME = "teste";
  public static final String LASTNAME = "example";
  public static final String DOCUMENT = "12312312312";
  public static final String EMAIL = "example@gmail.com";
  public static final UserType USER_TYPE = UserType.COMMON;
  public static final String PASSWORD = "password";
  public static final BigDecimal BALANCE = new BigDecimal("100.00");
  public static final UserRole USER_ROLE = UserRole.USER;


  @InjectMocks
  private UserJpaRepositoryImp userJpaRepositoryImp;

  @Mock
  private UserJpaRepository jpaRepository;

  @Mock
  private UserMapper mapper;

  private UserEntity mockUserEntity;
  private User mockUserDomain;

  @BeforeEach
  void setUp() {
    mockUserEntity = new UserEntity(ID, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE, USER_ROLE);
    mockUserDomain = new User(ID, FIRSTNAME, LASTNAME, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE, USER_ROLE);
  }

  @Test
  void findById_ShouldReturnUser_WhenUserExists() {
    // Arrange
    when(jpaRepository.findById(ID)).thenReturn(Optional.of(mockUserEntity));
    when(mapper.toDomain(any())).thenReturn(mockUserDomain);

    // Act
    Optional<User> userResult = userJpaRepositoryImp.findById(ID);

    // Assert
    assertAll(
        () -> assertTrue(userResult.isPresent()),
        () -> assertEquals(Optional.class, userResult.getClass()),
        () -> assertEquals(ID, userResult.get().getId()),
        () -> assertEquals(FIRSTNAME, userResult.get().getFirstName()),
        () -> assertEquals(LASTNAME, userResult.get().getLastName()),
        () -> assertEquals(DOCUMENT, userResult.get().getDocument()),
        () -> assertEquals(EMAIL, userResult.get().getEmail()),
        () -> assertEquals(PASSWORD, userResult.get().getPassword())
    );

    verify(jpaRepository).findById(ID);
    verify(mapper).toDomain(mockUserEntity);
  }

  @Test
  void findById_ShouldReturnEmpty_WhenUserDoesNotExist() {
    // Arrange
    when(jpaRepository.findById(ID)).thenReturn(Optional.empty());

    // Act
    Optional<User> userResult = userJpaRepositoryImp.findById(ID);

    // Assert
    assertAll(
        () -> assertTrue(userResult.isEmpty()),
        () -> assertEquals(Optional.class, userResult.getClass())
    );

    verify(jpaRepository).findById(ID);
    verify(mapper, never()).toDomain(any());
  }

  @Test
  void save_ShouldReturnUser_WhenUserIsSaved() {
    // Arrange
    when(mapper.toEntity(mockUserDomain)).thenReturn(mockUserEntity);
    when(jpaRepository.save(mockUserEntity)).thenReturn(mockUserEntity);
    when(mapper.toDomain(mockUserEntity)).thenReturn(mockUserDomain);

    // Act
    User userResult = userJpaRepositoryImp.save(mockUserDomain);

    // Assert
    assertAll(
        () -> assertNotNull(userResult),
        () -> assertEquals(User.class, userResult.getClass()),
        () -> assertEquals(ID, userResult.getId()),
        () -> assertEquals(FIRSTNAME, userResult.getFirstName()),
        () -> assertEquals(LASTNAME, userResult.getLastName()),
        () -> assertEquals(DOCUMENT, userResult.getDocument()),
        () -> assertEquals(EMAIL, userResult.getEmail()),
        () -> assertEquals(PASSWORD, userResult.getPassword())
    );

    verify(jpaRepository).save(mockUserEntity);
    verify(mapper).toEntity(userResult);
  }

  @Test
  void findByDocument_ShouldReturnUser_WhenUserExists() {
    // Arrange
    when(jpaRepository.findByDocument(DOCUMENT)).thenReturn(Optional.of(mockUserEntity));
    when(mapper.toDomain(mockUserEntity)).thenReturn(mockUserDomain);

    // act
    Optional<User> userResult = userJpaRepositoryImp.findByDocument(DOCUMENT);

    //assert
    assertAll(
        () -> assertTrue(userResult.isPresent()),
        () -> assertEquals(Optional.class, userResult.getClass()),
        () -> assertEquals(ID, userResult.get().getId()),
        () -> assertEquals(FIRSTNAME, userResult.get().getFirstName()),
        () -> assertEquals(LASTNAME, userResult.get().getLastName()),
        () -> assertEquals(DOCUMENT, userResult.get().getDocument()),
        () -> assertEquals(EMAIL, userResult.get().getEmail()),
        () -> assertEquals(PASSWORD, userResult.get().getPassword())
    );

    verify(jpaRepository).findByDocument(DOCUMENT);
    verify(mapper).toDomain(mockUserEntity);
  }

  @Test
  void findByDocument_ShouldReturnEmpty_WhenUserDoesNotExist() {
    // Arrange
    when(jpaRepository.findByDocument(DOCUMENT)).thenReturn(Optional.empty());

    // Act
    Optional<User> userResult = userJpaRepositoryImp.findByDocument(DOCUMENT);

    // Assert
    assertAll(
        () -> assertTrue(userResult.isEmpty()),
        () -> assertEquals(Optional.class, userResult.getClass())
    );

    verify(jpaRepository).findByDocument(DOCUMENT);
    verify(mapper, never()).toDomain(any());
  }

  @Test
  void findByEmail_ShouldReturnUser_WhenUserExists() {
    // Arrange
    when(jpaRepository.findByEmail(EMAIL)).thenReturn(Optional.of(mockUserEntity));
    when(mapper.toDomain(mockUserEntity)).thenReturn(mockUserDomain);

    // Act
    Optional<User> userResult = userJpaRepositoryImp.findByEmail(EMAIL);

    // Assert
    assertAll(
        () -> assertTrue(userResult.isPresent()),
        () -> assertEquals(Optional.class, userResult.getClass()),
        () -> assertEquals(ID, userResult.get().getId()),
        () -> assertEquals(FIRSTNAME, userResult.get().getFirstName()),
        () -> assertEquals(LASTNAME, userResult.get().getLastName()),
        () -> assertEquals(DOCUMENT, userResult.get().getDocument()),
        () -> assertEquals(EMAIL, userResult.get().getEmail()),
        () -> assertEquals(PASSWORD, userResult.get().getPassword())
    );

    verify(jpaRepository).findByEmail(EMAIL);
    verify(mapper).toDomain(mockUserEntity);
  }

  @Test
  void findByEmail_ShouldReturnEmpty_WhenUserDoesNotExist() {
    // Arrange
    when(jpaRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

    // Act
    Optional<User> userResult = userJpaRepositoryImp.findByEmail(EMAIL);

    // Assert
    assertAll(
        () -> assertInstanceOf(Optional.class, userResult),
        () -> assertTrue(userResult.isEmpty())
    );

    verify(jpaRepository).findByEmail(EMAIL);
    verify(mapper, never()).toDomain(any());
  }

  @Test
  void findAll_ShouldReturnListOfUsers_WhenUsersExist() {
    // Arrange
    when(jpaRepository.findAll()).thenReturn(List.of(mockUserEntity));
    when(mapper.toDomain(mockUserEntity)).thenReturn(mockUserDomain);

    // Act
    List<User> usersResult = userJpaRepositoryImp.findAll();

    // Assert
    assertAll(
        () -> assertInstanceOf(List.class, usersResult),
        () -> assertEquals(1 ,usersResult.size()),
        () -> assertEquals(User.class, usersResult.get(0).getClass()),
        () -> assertEquals(ID, usersResult.get(0).getId()),
        () -> assertEquals(FIRSTNAME, usersResult.get(0).getFirstName()),
        () -> assertEquals(LASTNAME, usersResult.get(0).getLastName()),
        () -> assertEquals(DOCUMENT, usersResult.get(0).getDocument()),
        () -> assertEquals(EMAIL, usersResult.get(0).getEmail()),
        () -> assertEquals(PASSWORD, usersResult.get(0).getPassword())
    );

    verify(jpaRepository).findAll();
    verify(mapper).toDomain(mockUserEntity);
  }

  @Test
  void findAll_ShouldReturnEmptyList_WhenNoUsersExist() {
    // Arrange
    when(jpaRepository.findAll()).thenReturn(List.of());

    // Act
    List<User> users = userJpaRepositoryImp.findAll();

    // Assert
    assertAll(
        () -> assertInstanceOf(List.class, users),
        () -> assertTrue(users.isEmpty())
    );

    verify(jpaRepository).findAll();
    verify(mapper, never()).toDomain(any());
  }

}
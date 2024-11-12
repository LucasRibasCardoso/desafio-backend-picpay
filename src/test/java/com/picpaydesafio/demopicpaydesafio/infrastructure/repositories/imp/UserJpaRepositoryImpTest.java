package com.picpaydesafio.demopicpaydesafio.infrastructure.repositories.imp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.UserEntity;
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

  private static final long ID = 1L;
  private static final String JOAO = "João";
  private static final String CARVALHO = "Carvalho";
  private static final String DOCUMENT_JOAO = "98515432130";
  private static final String EMAIL_JOAO = "joao@gmail.com";
  private static final String PASSWORD_JOAO = "joao123";
  private static final BigDecimal BALANCE = BigDecimal.valueOf(500);
  private static final UserType USER_TYPE = UserType.COMMON;

  private UserEntity userEntity;
  private User userDomain;

  @InjectMocks
  private UserJpaRepositoryImp userJpaRepositoryImp;

  @Mock
  private UserJpaRepository jpaRepository;

  @Mock
  private UserMapper mapper;

  @BeforeEach
  void setUp() {
    initializeTestObjects();
  }

  @Test
  void findById_ShouldReturnUser_WhenUserExists() {
    // Arrange
    when(jpaRepository.findById(ID)).thenReturn(Optional.of(userEntity));
    when(mapper.toDomain(any())).thenReturn(userDomain);

    // Act
    Optional<User> userResult = userJpaRepositoryImp.findById(ID);

    // Assert
    assertTrue(userResult.isPresent());
    assertEquals(Optional.class, userResult.getClass());

    assertEquals(ID, userResult.get().getId());
    assertEquals(JOAO, userResult.get().getFirstName());
    assertEquals(CARVALHO, userResult.get().getLastName());
    assertEquals(DOCUMENT_JOAO, userResult.get().getDocument());
    assertEquals(EMAIL_JOAO, userResult.get().getEmail());
    assertEquals(PASSWORD_JOAO, userResult.get().getPassword());

    verify(jpaRepository).findById(ID);
    verify(mapper).toDomain(userEntity);
  }

  @Test
  void findById_ShouldReturnEmpty_WhenUserDoesNotExist() {
    // Arrange
    when(jpaRepository.findById(ID)).thenReturn(Optional.empty());

    // Act
    Optional<User> userResult = userJpaRepositoryImp.findById(ID);

    // Assert
    assertTrue(userResult.isEmpty());
    assertEquals(Optional.class, userResult.getClass());

    verify(jpaRepository).findById(ID);
    verify(mapper, never()).toDomain(any());
  }

  @Test
  void save_ShouldReturnUser_WhenUserIsSaved() {
    // Arrange
    when(mapper.toEntity(userDomain)).thenReturn(userEntity);
    when(jpaRepository.save(userEntity)).thenReturn(userEntity);
    when(mapper.toDomain(userEntity)).thenReturn(userDomain);

    // Act
    User userResult = userJpaRepositoryImp.save(userDomain);

    // Assert
    assertNotNull(userResult);
    assertEquals(User.class, userResult.getClass());

    assertEquals(ID, userResult.getId());
    assertEquals(JOAO, userResult.getFirstName());
    assertEquals(CARVALHO, userResult.getLastName());
    assertEquals(DOCUMENT_JOAO, userResult.getDocument());
    assertEquals(EMAIL_JOAO, userResult.getEmail());
    assertEquals(PASSWORD_JOAO, userResult.getPassword());

    verify(jpaRepository).save(userEntity);
    verify(mapper).toEntity(userResult);
  }

  @Test
  void findByDocument_ShouldReturnUser_WhenUserExists() {
    // Arrange
    when(jpaRepository.findByDocument(DOCUMENT_JOAO)).thenReturn(Optional.of(userEntity));
    when(mapper.toDomain(userEntity)).thenReturn(userDomain);

    // act
    Optional<User> userResult = userJpaRepositoryImp.findByDocument(DOCUMENT_JOAO);

    //assert
    assertTrue(userResult.isPresent());
    assertEquals(Optional.class, userResult.getClass());

    assertEquals(ID, userResult.get().getId());
    assertEquals(JOAO, userResult.get().getFirstName());
    assertEquals(CARVALHO, userResult.get().getLastName());
    assertEquals(DOCUMENT_JOAO, userResult.get().getDocument());
    assertEquals(EMAIL_JOAO, userResult.get().getEmail());
    assertEquals(PASSWORD_JOAO, userResult.get().getPassword());

    verify(jpaRepository).findByDocument(DOCUMENT_JOAO);
    verify(mapper).toDomain(userEntity);
  }

  @Test
  void findByDocument_ShouldReturnEmpty_WhenUserDoesNotExist() {
    // Arrange
    when(jpaRepository.findByDocument(DOCUMENT_JOAO)).thenReturn(Optional.empty());

    // Act
    Optional<User> userResult = userJpaRepositoryImp.findByDocument(DOCUMENT_JOAO);

    // Assert
    assertTrue(userResult.isEmpty());
    assertEquals(Optional.class, userResult.getClass());

    verify(jpaRepository).findByDocument(DOCUMENT_JOAO);
    verify(mapper, never()).toDomain(any());
  }

  @Test
  void findByEmail_ShouldReturnUser_WhenUserExists() {
    // Arrange
    when(jpaRepository.findByEmail(EMAIL_JOAO)).thenReturn(Optional.of(userEntity));
    when(mapper.toDomain(userEntity)).thenReturn(userDomain);

    // Act
    Optional<User> userResult = userJpaRepositoryImp.findByEmail(EMAIL_JOAO);

    // Assert
    assertTrue(userResult.isPresent());
    assertEquals(Optional.class, userResult.getClass());

    assertEquals(ID, userResult.get().getId());
    assertEquals(JOAO, userResult.get().getFirstName());
    assertEquals(CARVALHO, userResult.get().getLastName());
    assertEquals(DOCUMENT_JOAO, userResult.get().getDocument());
    assertEquals(EMAIL_JOAO, userResult.get().getEmail());
    assertEquals(PASSWORD_JOAO, userResult.get().getPassword());

    verify(jpaRepository).findByEmail(EMAIL_JOAO);
    verify(mapper).toDomain(userEntity);
  }

  @Test
  void findByEmail_ShouldReturnEmpty_WhenUserDoesNotExist() {
    // Arrange
    when(jpaRepository.findByEmail(EMAIL_JOAO)).thenReturn(Optional.empty());

    // Act
    Optional<User> userResult = userJpaRepositoryImp.findByEmail(EMAIL_JOAO);

    // Assert
    assertInstanceOf(Optional.class, userResult);
    assertTrue(userResult.isEmpty());

    verify(jpaRepository).findByEmail(EMAIL_JOAO);
    verify(mapper, never()).toDomain(any());
  }

  @Test
  void findAll_ShouldReturnListOfUsers_WhenUsersExist() {
    // Arrange
    when(jpaRepository.findAll()).thenReturn(List.of(userEntity));
    when(mapper.toDomain(userEntity)).thenReturn(userDomain);

    // Act
    List<User> usersResult = userJpaRepositoryImp.findAll();

    // Assert
    assertInstanceOf(List.class, usersResult);
    assertEquals(1 ,usersResult.size());
    assertEquals(User.class, usersResult.get(0).getClass());

    assertEquals(ID, usersResult.get(0).getId());
    assertEquals(JOAO, usersResult.get(0).getFirstName());
    assertEquals(CARVALHO, usersResult.get(0).getLastName());
    assertEquals(DOCUMENT_JOAO, usersResult.get(0).getDocument());
    assertEquals(EMAIL_JOAO, usersResult.get(0).getEmail());
    assertEquals(PASSWORD_JOAO, usersResult.get(0).getPassword());

    verify(jpaRepository).findAll();
    verify(mapper).toDomain(userEntity);
  }

  @Test
  void findAll_ShouldReturnEmptyList_WhenNoUsersExist() {
    // Arrange
    when(jpaRepository.findAll()).thenReturn(List.of());

    // Act
    List<User> users = userJpaRepositoryImp.findAll();

    // Assert
    assertInstanceOf(List.class, users);
    assertTrue(users.isEmpty());

    verify(jpaRepository).findAll();
    verify(mapper, never()).toDomain(any());
  }

  private void initializeTestObjects() {
    userEntity = new UserEntity(ID, JOAO, CARVALHO, DOCUMENT_JOAO, EMAIL_JOAO, PASSWORD_JOAO, BALANCE, USER_TYPE);

    userDomain = new User(ID, JOAO, CARVALHO, DOCUMENT_JOAO, EMAIL_JOAO, PASSWORD_JOAO, BALANCE, USER_TYPE);
  }

}
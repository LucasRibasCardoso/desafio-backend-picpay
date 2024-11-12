package com.picpaydesafio.demopicpaydesafio.infrastructure.repositories.imp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.UserEntity;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.infrastructure.mappers.UserMapper;
import com.picpaydesafio.demopicpaydesafio.infrastructure.repositories.UserJpaRepository;
import java.math.BigDecimal;
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
    assertEquals(userDomain, userResult.get());
    verify(jpaRepository).findById(ID);
    verify(mapper).toDomain(userEntity);
    assertEquals(Optional.class, userResult.getClass());
  }

  @Test
  void findById_ShouldReturnEmpty_WhenUserDoesNotExist() {
    // Arrange
    when(jpaRepository.findById(ID)).thenReturn(Optional.empty());

    // Act
    Optional<User> userResult = userJpaRepositoryImp.findById(ID);

    // Assert
    assertTrue(userResult.isEmpty());
    verify(jpaRepository).findById(ID);
    assertEquals(Optional.class, userResult.getClass());
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
    assertEquals(userDomain, userResult);
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
    assertEquals(userDomain, userResult.get());
    verify(jpaRepository).findByDocument(DOCUMENT_JOAO);
    verify(mapper).toDomain(userEntity);
    assertEquals(Optional.class, userResult.getClass());
  }

  @Test
  void findByDocument_ShouldReturnEmpty_WhenUserDoesNotExist() {
    // Arrange
    when(jpaRepository.findByDocument(DOCUMENT_JOAO)).thenReturn(Optional.empty());

    // Act
    Optional<User> userResult = userJpaRepositoryImp.findByDocument(DOCUMENT_JOAO);

    // Assert
    assertTrue(userResult.isEmpty());
    verify(jpaRepository).findByDocument(DOCUMENT_JOAO);
    assertEquals(Optional.class, userResult.getClass());
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
    assertEquals(userDomain, userResult.get());
    verify(jpaRepository).findByEmail(EMAIL_JOAO);
    verify(mapper).toDomain(userEntity);
    assertEquals(Optional.class, userResult.getClass());
  }

  @Test
  void findByEmail_ShouldReturnEmpty_WhenUserDoesNotExist() {
    // Arrange
    when(jpaRepository.findByEmail(EMAIL_JOAO)).thenReturn(Optional.empty());

    // Act
    Optional<User> userResult = userJpaRepositoryImp.findByEmail(EMAIL_JOAO);

    // Assert
    assertTrue(userResult.isEmpty());
    verify(jpaRepository).findByEmail(EMAIL_JOAO);
    assertEquals(Optional.class, userResult.getClass());
  }

  @Test
  void findAll() {
  }

  private void initializeTestObjects() {
    userEntity = new UserEntity(ID, JOAO, CARVALHO, DOCUMENT_JOAO, EMAIL_JOAO, PASSWORD_JOAO, BALANCE, USER_TYPE);

    userDomain = new User(ID, JOAO, CARVALHO, DOCUMENT_JOAO, EMAIL_JOAO, PASSWORD_JOAO, BALANCE, USER_TYPE);
  }

}
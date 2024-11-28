package com.picpaydesafio.demopicpaydesafio.application.services.imp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.application.services.EmailSendingService;
import com.picpaydesafio.demopicpaydesafio.application.usecases.CreateTransactionUseCase;
import com.picpaydesafio.demopicpaydesafio.domain.models.Transaction;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesDomain.TransactionRepository;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.infrastructure.mappers.TransactionMapper;
import com.picpaydesafio.demopicpaydesafio.web.dtos.TransactionRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.TransactionResponseDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImpTest {

  public static final BigDecimal AMOUNT = new BigDecimal(200);
  public static final long ID_TRANSACTION = 1L;
  public static final long SENDER_ID = ID_TRANSACTION;
  public static final long RECEIVER_ID = 2L;

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

  private static final UserType USER_TYPE = UserType.COMMON;
  public static final LocalDateTime TIMESTAMP = LocalDateTime.now();

  private TransactionRequestDTO requestDTO;
  private TransactionResponseDTO responseDTO;
  private Transaction transactionDomain;
  private User sender;
  private User receiver;

  @InjectMocks
  private TransactionServiceImp transactionServiceImp;

  @Mock
  private TransactionRepository transactionRepository;
  @Mock
  private TransactionMapper transactionMapper;
  @Mock
  private CreateTransactionUseCase createTransactionUseCase;
  @Mock
  private EmailSendingService emailService;

  @BeforeEach
  void setUp() {
    initializeTestObjects();
  }

  @Test
  void createTransaction_ThenReturnTransactionResponseDTO() {
    // Arrange
    when(transactionMapper.toResponseDTO(transactionDomain)).thenReturn(responseDTO);
    when(createTransactionUseCase.execute(any())).thenReturn(transactionDomain);
    doNothing().when(emailService).sendEmail(any());
    // Act
    TransactionResponseDTO result = transactionServiceImp.createTransaction(requestDTO);

    // Assert
    assertNotNull(result);
    assertInstanceOf(TransactionResponseDTO.class, result);

    assertEquals(responseDTO.id(), result.id());
    assertEquals(responseDTO.senderId(), result.senderId());
    assertEquals(responseDTO.senderName(), result.senderName());
    assertEquals(responseDTO.receiverId(), result.receiverId());
    assertEquals(responseDTO.receiverName(), result.receiverName());
    assertEquals(responseDTO.amount(), result.amount());
    assertEquals(responseDTO.timestamp(), result.timestamp());

    verify(transactionMapper).toResponseDTO(transactionDomain);
    verify(createTransactionUseCase).execute(any());
    verify(emailService).sendEmail(any());
  }

  @Test
  void getAllTransactions_ThenReturnListOfTransactionResponseDTO() {
    // Arrange
    when(transactionRepository.findAll()).thenReturn(List.of(transactionDomain));
    when(transactionMapper.toResponseDTO(transactionDomain)).thenReturn(responseDTO);

    // act
    List<TransactionResponseDTO> transactionsDTO = transactionServiceImp.getAllTransactions();

    // assert
    assertNotNull(transactionsDTO);
    assertFalse(transactionsDTO.isEmpty());
    assertInstanceOf(List.class, transactionsDTO);

    assertEquals(responseDTO.id(), transactionsDTO.get(0).id());
    assertEquals(responseDTO.senderId(), transactionsDTO.get(0).senderId());
    assertEquals(responseDTO.senderName(), transactionsDTO.get(0).senderName());
    assertEquals(responseDTO.receiverId(), transactionsDTO.get(0).receiverId());
    assertEquals(responseDTO.receiverName(), transactionsDTO.get(0).receiverName());
    assertEquals(responseDTO.amount(), transactionsDTO.get(0).amount());
    assertEquals(responseDTO.timestamp(), transactionsDTO.get(0).timestamp());

    verify(transactionRepository).findAll();
    verify(transactionMapper).toResponseDTO(transactionDomain);
  }

  @Test
  void getAllTransaction_ThenReturnEmptyListOfTransactionResponseDTO() {
    // arrange
    when(transactionRepository.findAll()).thenReturn(List.of());

    // act
    List<TransactionResponseDTO> transactionsDTO = transactionServiceImp.getAllTransactions();

    // arrange
    assertNotNull(transactionsDTO);
    assertInstanceOf(List.class, transactionsDTO);
    assertTrue(transactionsDTO.isEmpty());

    verify(transactionRepository).findAll();
    verifyNoInteractions(transactionMapper);
  }

  private void initializeTestObjects() {
    sender = new User(SENDER_ID, JOAO, CARVALHO, DOCUMENT, EMAIL, PASSWORD, BALANCE, USER_TYPE);

    receiver = new User(
        RECEIVER_ID, MARIA, SILVA, DOCUMENT_MARIA, EMAIL_MARIA, PASSWORD_MARIA, BALANCE, USER_TYPE);

    requestDTO = new TransactionRequestDTO(SENDER_ID, RECEIVER_ID, AMOUNT);

    transactionDomain = new Transaction(ID_TRANSACTION, AMOUNT, sender, receiver, TIMESTAMP);

    responseDTO = new TransactionResponseDTO(ID_TRANSACTION, SENDER_ID, sender.fullName(), RECEIVER_ID,
        receiver.fullName(), AMOUNT, TIMESTAMP
    );
  }

}
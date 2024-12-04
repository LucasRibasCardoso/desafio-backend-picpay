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


  // User Sender
  public static final long ID_1 = 1L;
  public static final String FIRSTNAME_1 = "teste1";
  public static final String LASTNAME_1 = "example1";
  public static final String FULL_NAME_1 = FIRSTNAME_1 + " " + LASTNAME_1;
  public static final String DOCUMENT_1 = "12312312312";
  public static final String EMAIL_1 = "example1@gmail.com";
  public static final UserType USER_TYPE_1 = UserType.COMMON;
  public static final String PASSWORD_1 = "password";
  public static final BigDecimal BALANCE_1 = new BigDecimal("100.00");

  // User Receiver
  public static final long ID_2 = 2L;
  public static final String FIRSTNAME_2 = "teste2";
  public static final String LASTNAME_2 = "example2";
  public static final String FULL_NAME_2 = FIRSTNAME_2 + " " + LASTNAME_2;
  public static final String DOCUMENT_2 = "12312312312";
  public static final String EMAIL_2 = "example2@gmail.com";
  public static final UserType USER_TYPE_2 = UserType.COMMON;
  public static final String PASSWORD_2 = "password";
  public static final BigDecimal BALANCE_2 = new BigDecimal("100.00");

  // Transaction
  public static final long ID_TRANSACTION = 1L;
  public static final BigDecimal AMOUNT = new BigDecimal(200);
  public static final LocalDateTime TIMESTAMP = LocalDateTime.now();

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

  private TransactionRequestDTO mockTransactionRequest;
  private TransactionResponseDTO mockTransactionResponse;
  private Transaction mockTransaction;
  private User mockSender;
  private User mockReceiver;

  @BeforeEach
  void setUp() {
    mockSender = new User(
        ID_1, FIRSTNAME_1, LASTNAME_1, DOCUMENT_1, EMAIL_1, PASSWORD_1, BALANCE_1, USER_TYPE_1
    );
    mockReceiver = new User(
        ID_2, FIRSTNAME_2, LASTNAME_2, DOCUMENT_2, EMAIL_2, PASSWORD_2, BALANCE_2, USER_TYPE_2
    );
    mockTransactionResponse = new TransactionResponseDTO(
        ID_TRANSACTION, ID_1, FULL_NAME_1, ID_2, FULL_NAME_2, AMOUNT, TIMESTAMP
    );

    mockTransaction = new Transaction(ID_TRANSACTION, AMOUNT, mockSender, mockReceiver, TIMESTAMP);
    mockTransactionRequest = new TransactionRequestDTO(ID_1, ID_2, AMOUNT);
  }

  @Test
  void createTransaction_ThenReturnTransactionResponseDTO() {
    // Arrange
    when(transactionMapper.toResponseDTO(mockTransaction)).thenReturn(mockTransactionResponse);
    when(createTransactionUseCase.execute(any())).thenReturn(mockTransaction);
    doNothing().when(emailService).sendEmail(any());

    // Act
    TransactionResponseDTO result = transactionServiceImp.createTransaction(mockTransactionRequest);

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertInstanceOf(TransactionResponseDTO.class, result),
        () -> assertEquals(mockTransactionResponse.id(), result.id()),
        () -> assertEquals(mockTransactionResponse.senderId(), result.senderId()),
        () -> assertEquals(mockTransactionResponse.senderName(), result.senderName()),
        () -> assertEquals(mockTransactionResponse.receiverId(), result.receiverId()),
        () -> assertEquals(mockTransactionResponse.receiverName(), result.receiverName()),
        () -> assertEquals(mockTransactionResponse.amount(), result.amount()),
        () -> assertEquals(mockTransactionResponse.timestamp(), result.timestamp())
    );

    verify(transactionMapper).toResponseDTO(mockTransaction);
    verify(createTransactionUseCase).execute(any());
    verify(emailService).sendEmail(any());
  }

  @Test
  void getAllTransactions_ThenReturnListOfTransactionResponseDTO() {
    // Arrange
    when(transactionRepository.findAll()).thenReturn(List.of(mockTransaction));
    when(transactionMapper.toResponseDTO(mockTransaction)).thenReturn(mockTransactionResponse);

    // Act
    List<TransactionResponseDTO> transactionsDTO = transactionServiceImp.getAllTransactions();

    // Assert
    assertAll(
        () -> assertNotNull(transactionsDTO),
        () -> assertFalse(transactionsDTO.isEmpty()),
        () -> assertInstanceOf(List.class, transactionsDTO),
        () -> assertEquals(mockTransactionResponse.id(), transactionsDTO.get(0).id()),
        () -> assertEquals(mockTransactionResponse.senderId(), transactionsDTO.get(0).senderId()),
        () -> assertEquals(mockTransactionResponse.senderName(), transactionsDTO.get(0).senderName()),
        () -> assertEquals(mockTransactionResponse.receiverId(), transactionsDTO.get(0).receiverId()),
        () -> assertEquals(mockTransactionResponse.receiverName(), transactionsDTO.get(0).receiverName()),
        () -> assertEquals(mockTransactionResponse.amount(), transactionsDTO.get(0).amount()),
        () -> assertEquals(mockTransactionResponse.timestamp(), transactionsDTO.get(0).timestamp())
    );

    verify(transactionRepository).findAll();
    verify(transactionMapper).toResponseDTO(mockTransaction);
  }

  @Test
  void getAllTransaction_ThenReturnEmptyList_WhenDoesNotExistTransactions() {
    // Arrange
    when(transactionRepository.findAll()).thenReturn(List.of());

    // Act
    List<TransactionResponseDTO> transactionsDTO = transactionServiceImp.getAllTransactions();

    // Arrange
    assertAll(
        () -> assertNotNull(transactionsDTO),
        () -> assertInstanceOf(List.class, transactionsDTO),
        () -> assertTrue(transactionsDTO.isEmpty())
    );

    verify(transactionRepository).findAll();
    verifyNoInteractions(transactionMapper);
  }


}
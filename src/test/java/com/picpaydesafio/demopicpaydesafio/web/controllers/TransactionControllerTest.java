package com.picpaydesafio.demopicpaydesafio.web.controllers;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picpaydesafio.demopicpaydesafio.application.services.TransactionService;
import com.picpaydesafio.demopicpaydesafio.web.dtos.TransactionRequestDTO;
import com.picpaydesafio.demopicpaydesafio.web.dtos.TransactionResponseDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;




@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

  // Transaction
  private static final long ID_SENDER = 1L;
  public static final String FIRSTNAME_SENDER = "teste";
  private static final long ID_RECEIVER = 2L;
  public static final String FIRSTNAME_RECEIVER = "teste";
  private static final BigDecimal AMOUNT = new BigDecimal("100.00");
  public static final LocalDateTime TIMESTAMP = LocalDateTime.now();

  public static final long ID = 1L;


  @InjectMocks
  private TransactionController transactionController;

  @Mock
  private TransactionService transactionService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private TransactionRequestDTO mockTransactionRequest;
  private TransactionResponseDTO mockTransactionResponse;

  @BeforeEach
  void setUp() {
    mockTransactionRequest = new TransactionRequestDTO(ID_SENDER, ID_RECEIVER, AMOUNT);
    mockTransactionResponse = new TransactionResponseDTO(
        ID, ID_SENDER, FIRSTNAME_SENDER, ID_RECEIVER, FIRSTNAME_RECEIVER, AMOUNT, TIMESTAMP
    );
  }

  @Test
  void createTransaction_ShouldReturnTransactionResponseDTO() {
    // Arrange
    when(transactionService.createTransaction(mockTransactionRequest))
        .thenReturn(mockTransactionResponse);

    // Act
    ResponseEntity<TransactionResponseDTO> result =
        transactionController.createTransaction(mockTransactionRequest);

    // Arrange
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(HttpStatus.CREATED, result.getStatusCode()),
        () -> assertEquals(mockTransactionResponse.id(), result.getBody().id()),
        () -> assertEquals(mockTransactionResponse.senderId(), result.getBody().senderId()),
        () -> assertEquals(mockTransactionResponse.senderName(), result.getBody().senderName()),
        () -> assertEquals(mockTransactionResponse.receiverId(), result.getBody().receiverId()),
        () -> assertEquals(mockTransactionResponse.receiverName(), result.getBody().receiverName()),
        () -> assertEquals(mockTransactionResponse.amount(), result.getBody().amount()),
        () -> assertEquals(mockTransactionResponse.timestamp(), result.getBody().timestamp())
    );

    verify(transactionService).createTransaction(mockTransactionRequest);
  }

  @Test
  @WithMockUser(username = "user@example.com", roles = "USER")
  void createTransaction_ShouldThrowException_WhenRequestFieldsAreInvalid() throws Exception {
    // Arrange
    TransactionRequestDTO invalidRequest = new TransactionRequestDTO(null, null, new BigDecimal("-50.00"));

    String invalidRequestJson = objectMapper.writeValueAsString(invalidRequest);

    // Act & Assert
    mockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidRequestJson))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Erro de validação"))
        .andExpect(jsonPath("$.statusCode").value(400))
        .andExpect(jsonPath("$.fieldErrors.senderId").value("O id do pagador precisa ser informado."))
        .andExpect(jsonPath("$.fieldErrors.receiverId").value("O id do recebedor precisa ser informado."))
        .andExpect(jsonPath("$.fieldErrors.amount").value("O valor da trânsferencia precisa ser maior que zero."));

  }

  @Test
  void findAllTransactions_ShouldReturnListOfTransactionResponseDTO_WhenTransactionsExist() {
    // Arrange
    when(transactionService.getAllTransactions()).thenReturn(List.of(mockTransactionResponse));

    // Act
    ResponseEntity<List<TransactionResponseDTO>> result = transactionController.findAllTransactions();

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(1, result.getBody().size()),
        () -> assertInstanceOf(TransactionResponseDTO.class, result.getBody().get(0)),
        () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
        () -> assertEquals(mockTransactionResponse.id(), result.getBody().get(0).id()),
        () -> assertEquals(mockTransactionResponse.senderId(), result.getBody().get(0).senderId()),
        () -> assertEquals(mockTransactionResponse.senderName(), result.getBody().get(0).senderName()),
        () -> assertEquals(mockTransactionResponse.receiverId(), result.getBody().get(0).receiverId()),
        () -> assertEquals(mockTransactionResponse.receiverName(), result.getBody().get(0).receiverName()),
        () -> assertEquals(mockTransactionResponse.amount(), result.getBody().get(0).amount()),
        () -> assertEquals(mockTransactionResponse.timestamp(), result.getBody().get(0).timestamp())
    );

  }

  @Test
  void findAllTransactions_ShouldReturnEmptyList_WhenNoTransactionsExist() {
    // Arrange
    when(transactionService.getAllTransactions()).thenReturn(Collections.emptyList());

    // Act
    ResponseEntity<List<TransactionResponseDTO>> result = transactionController.findAllTransactions();

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertTrue(result.getBody().isEmpty()),
        () -> assertInstanceOf(List.class, result.getBody()),
        () -> assertEquals(HttpStatus.OK, result.getStatusCode())
    );
  }
}

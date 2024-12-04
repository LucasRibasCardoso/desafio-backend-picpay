package com.picpaydesafio.demopicpaydesafio.application.services.imp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.web.dtos.EmailValidationResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class EmailValidatorServiceImpTest {

  private static final String API_KEY = "test-api-key";
  public static final String EMAIL = "test@example.com";

  @InjectMocks
  private EmailValidatorServiceImp emailValidatorServiceImp;

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private EmailValidationResponseDTO mockResponse;

  @Captor
  private ArgumentCaptor<String> urlCaptor;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(emailValidatorServiceImp, "API_KEY", API_KEY);
  }

  @Test
  void isValid_ShouldReturnTrue_WhenValidEmail() {
    // Arrange
    ReflectionTestUtils.setField(mockResponse, "deliverability", "DELIVERABLE");
    when(restTemplate.getForObject(anyString(), eq(EmailValidationResponseDTO.class))).thenReturn(mockResponse);
    when(mockResponse.isEmailValid()).thenReturn(true);

    // Act
    boolean result = emailValidatorServiceImp.isValid(EMAIL);

    // Assert
    assertTrue(result);
    verify(restTemplate).getForObject(urlCaptor.capture(), eq(EmailValidationResponseDTO.class));
  }

  @Test
  void isValid_shouldReturnFalse_whenEmailIsNotDeliverable() {
    // Arrange
    ReflectionTestUtils.setField(mockResponse, "deliverability", "UNDELIVERABLE");
    when(restTemplate.getForObject(anyString(), eq(EmailValidationResponseDTO.class))).thenReturn(mockResponse);
    when(mockResponse.isEmailValid()).thenReturn(false);

    // Act
    boolean result = emailValidatorServiceImp.isValid(EMAIL);

    // Assert
    assertFalse(result);
  }

  @Test
  void isValid_shouldReturnFalse_whenResponseIsNull() {
    // Arrange
    when(restTemplate.getForObject(anyString(), eq(EmailValidationResponseDTO.class))).thenReturn(null);

    // Act
    boolean result = emailValidatorServiceImp.isValid(EMAIL);

    // Assert
    assertFalse(result);
  }
}
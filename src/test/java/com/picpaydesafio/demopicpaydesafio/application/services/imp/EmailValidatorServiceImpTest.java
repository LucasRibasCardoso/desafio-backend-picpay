package com.picpaydesafio.demopicpaydesafio.application.services.imp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.picpaydesafio.demopicpaydesafio.web.dtos.EmailValidationResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class EmailValidatorServiceImpTest {

  @InjectMocks
  private EmailValidatorServiceImp emailValidatorServiceImp;

  @Mock
  private RestTemplate restTemplate;

  private static final String BASE_URL = "https://emailvalidation.abstractapi.com/v1";
  private static final String API_KEY = "test-api-key";

  private EmailValidationResponseDTO mockResponse;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(emailValidatorServiceImp, "API_KEY", "test-api-key");
    mockResponse = new EmailValidationResponseDTO();
  }

  @Test
  void isValid_ShouldReturnTrue_WhenValidEmail() {
    // arrange
    String testEmail = "test@example.com";
    EmailValidationResponseDTO mockResponse = new EmailValidationResponseDTO();
    ReflectionTestUtils.setField(mockResponse, "deliverability", "DELIVERABLE");

    String apiUrl = buildApiUrl(testEmail);
    when(restTemplate.getForObject(apiUrl, EmailValidationResponseDTO.class)).thenReturn(mockResponse);

    // act
    boolean result = emailValidatorServiceImp.isValid(testEmail);

    // assert
    assertTrue(result);
    verify(restTemplate, Mockito.times(1)).getForObject(apiUrl, EmailValidationResponseDTO.class);
  }

  @Test
  void isValid_shouldReturnFalse_whenEmailIsNotDeliverable() {
    // Arrange
    String testEmail = "invalid@example.com";
    ReflectionTestUtils.setField(mockResponse, "deliverability", "UNDELIVERABLE");

    String apiUrl = buildApiUrl(testEmail);
    when(restTemplate.getForObject(apiUrl, EmailValidationResponseDTO.class)).thenReturn(mockResponse);

    // Act
    boolean result = emailValidatorServiceImp.isValid(testEmail);

    // Assert
    assertFalse(result);
  }

  @Test
  void isValid_shouldReturnFalse_whenResponseIsNull() {
    // Arrange
    String testEmail = "null@example.com";

    String apiUrl = buildApiUrl(testEmail);
    when(restTemplate.getForObject(apiUrl, EmailValidationResponseDTO.class)).thenReturn(null);

    // Act
    boolean result = emailValidatorServiceImp.isValid(testEmail);

    // Assert
    assertFalse(result);
  }

  private String buildApiUrl(String email) {
    return BASE_URL + "?api_key=" + API_KEY + "&email=" + email;
  }
}
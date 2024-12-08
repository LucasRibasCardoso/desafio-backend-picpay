package com.picpaydesafio.demopicpaydesafio.web.dtos;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailValidationResponseDTOTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void isValidEmail_ShouldReturnTrue_WhenDeliverabilityIsDeliverable() throws Exception {
    // Arrange
    String jsonDeliverable = "{\"deliverability\": \"DELIVERABLE\"}";

    // Act
    EmailValidationResponseDTO dto = objectMapper.readValue(
        jsonDeliverable, EmailValidationResponseDTO.class);

    // Assert
    assertTrue(dto.isValidEmail());
  }

  @Test
  void isValidEmail_ShouldReturnFalse_WhenDeliverablityNotIsDeliverable() throws Exception {
    // Arrange
    String jsonUndeliverable = "{\"deliverability\": \"UNDELIVERABLE\"}";

    // Act
    EmailValidationResponseDTO dto = objectMapper.readValue(
        jsonUndeliverable, EmailValidationResponseDTO.class);

    // Assert
    assertFalse(dto.isValidEmail());
  }

}
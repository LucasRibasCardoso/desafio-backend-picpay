package com.picpaydesafio.demopicpaydesafio.web.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailValidationResponseDTO {

  @JsonProperty("deliverability")
  private String deliverability;


  public boolean isEmailValid() {
    return "DELIVERABLE".equalsIgnoreCase(this.deliverability != null ? this.deliverability : "");
  }

}
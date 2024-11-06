package com.picpaydesafio.demopicpaydesafio.domain.models;

import java.time.LocalDateTime;
import lombok.Value;

@Value
public class Email {

  private Long id;
  private String ownerRef;
  private String emailFrom;
  private String emailTo;
  private String subject;
  private String text;
  private LocalDateTime sendDateEmail;


}

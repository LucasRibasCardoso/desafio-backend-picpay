package com.picpaydesafio.demopicpaydesafio.application.email.service;

import com.picpaydesafio.demopicpaydesafio.application.email.exceptions.InvalidSendEmail;
import com.picpaydesafio.demopicpaydesafio.domain.email.factory.EmailFactory;
import com.picpaydesafio.demopicpaydesafio.domain.email.mapper.EmailMapper;
import com.picpaydesafio.demopicpaydesafio.domain.email.model.Email;
import com.picpaydesafio.demopicpaydesafio.domain.transction.model.Transaction;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImp implements EmailService {

  private final JavaMailSender emailSender;
  private final EmailFactory emailFactory;
  private final EmailMapper emailMapper;


  public void sendTransactionEmail(Transaction transaction) {
    sendEmailToSender(transaction);
    sendEmailToReceiver(transaction);
  }

  private void sendEmailToSender(Transaction transaction) {
    Email email = getEmailForSender(transaction);
    sendEmail(email);
  }

  private void sendEmailToReceiver(Transaction transaction) {
    Email email = getEmailForReceiver(transaction);
    sendEmail(email);
  }

  private void sendEmail(Email email) {
    try {
      SimpleMailMessage message = emailMapper.toMailMessage(email);
      emailSender.send(message);
    }
    catch (MailException e) {
      throw new InvalidSendEmail("Erro ao enviar email. Tente realizar a transação novamente mais tarde.");
    }
  }

  private Email getEmailForSender(Transaction transaction) {
    String ownerEmail = transaction.getSender().fullName();
    String emailTo = transaction.getSender().getEmail();
    String subject = "PixPlay - Transação realizada";

    StringBuilder textBuilder = new StringBuilder();

    textBuilder.append("Olá, " + transaction.getSender().fullName() + "!\n");
    textBuilder.append("Você realizou uma transferência de R$" + transaction.getAmount());
    textBuilder.append(" para " + transaction.getReceiver().fullName() + ".\n");

    String text = textBuilder.toString();

    return emailFactory.createDomain(null, ownerEmail, emailTo, subject, text, LocalDateTime.now());
  }

  private Email getEmailForReceiver(Transaction transaction) {
    String ownerEmail = transaction.getReceiver().fullName();
    String emailTo = transaction.getReceiver().getEmail();
    String subject = "PixPlay - Você recebeu uma transferência";

    StringBuilder textBuilder = new StringBuilder();
    textBuilder.append("Olá, " + transaction.getReceiver().fullName() + "!\n");
    textBuilder.append("Você recebeu uma transferência de R$" + transaction.getAmount());
    textBuilder.append(" de " + transaction.getSender().fullName() + ".\n");

    String text = textBuilder.toString();

    return emailFactory.createDomain(null, ownerEmail, emailTo, subject, text, LocalDateTime.now());
  }

}

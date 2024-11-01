package com.picpaydesafio.demopicpaydesafio.services.notification;

import com.picpaydesafio.demopicpaydesafio.domain.user.User;
import com.picpaydesafio.demopicpaydesafio.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationServiceImp implements NotificationService {

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public void sendNotification(User user, String message) {
    String email = user.getEmail();
    NotificationDTO notificationRequest = new NotificationDTO(email, message);

    // endpoint saiu fora do ar
    ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);

    // if (!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
    // throw new OfflineService("Serviço de notificação está fora do ar.");
    //}
  }
}

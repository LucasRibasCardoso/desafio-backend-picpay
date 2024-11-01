package com.picpaydesafio.demopicpaydesafio.services.notification;

import com.picpaydesafio.demopicpaydesafio.domain.user.User;

public interface NotificationService {
  void sendNotification(User user, String message);
}

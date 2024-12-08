package com.picpaydesafio.demopicpaydesafio.domain.factories;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserRequestDTO;
import org.springframework.stereotype.Component;

@Component
public interface UserFactory {

  User createDomain(UserRequestDTO userRequest);
  User createDomain(UserRequestDTO userRequest, String encryptedPassword);;
}

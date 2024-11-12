package com.picpaydesafio.demopicpaydesafio.domain.factories;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserRequestDTO;

public interface UserFactory {

  User createDomain(UserRequestDTO userRequest);
}
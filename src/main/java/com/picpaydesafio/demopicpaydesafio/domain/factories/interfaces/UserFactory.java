package com.picpaydesafio.demopicpaydesafio.domain.factories.interfaces;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.web.user.dtos.UserRequestDTO;

public interface UserFactory {

  User createDomain(UserRequestDTO userRequest);
}

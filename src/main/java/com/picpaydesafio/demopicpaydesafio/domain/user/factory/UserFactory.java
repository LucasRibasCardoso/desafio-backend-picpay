package com.picpaydesafio.demopicpaydesafio.domain.user.factory;

import com.picpaydesafio.demopicpaydesafio.domain.user.model.User;
import com.picpaydesafio.demopicpaydesafio.web.user.dtos.UserRequestDTO;

public interface UserFactory {

  User createDomain(UserRequestDTO userRequest);
}

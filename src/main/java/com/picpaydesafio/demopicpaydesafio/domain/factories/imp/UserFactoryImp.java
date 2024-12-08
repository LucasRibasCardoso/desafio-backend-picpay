package com.picpaydesafio.demopicpaydesafio.domain.factories.imp;

import com.picpaydesafio.demopicpaydesafio.domain.factories.UserFactory;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserRole;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserRequestDTO;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactoryImp implements UserFactory {


  @Override
  public User createDomain(UserRequestDTO userRequest) {
    return new User(
        null,
        userRequest.firstName(),
        userRequest.lastName(),
        userRequest.document(),
        userRequest.email(),
        userRequest.password(),
        new BigDecimal(0),
        UserType.valueOf(userRequest.userType()),
        UserRole.valueOf(userRequest.role())
    );
  }

  @Override
  public User createDomain(UserRequestDTO userRequest, String encryptedPassword) {
    return new User(
        null,
        userRequest.firstName(),
        userRequest.lastName(),
        userRequest.document(),
        userRequest.email(),
        encryptedPassword,
        new BigDecimal(0),
        UserType.valueOf(userRequest.userType()),
        UserRole.valueOf(userRequest.role())
    );
  }

}

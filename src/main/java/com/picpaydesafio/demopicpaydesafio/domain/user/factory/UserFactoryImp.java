package com.picpaydesafio.demopicpaydesafio.domain.user.factory;

import com.picpaydesafio.demopicpaydesafio.domain.user.model.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.user.entity.enums.UserType;
import com.picpaydesafio.demopicpaydesafio.web.user.dtos.UserRequestDTO;
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
        UserType.valueOf(userRequest.userType())
    );
  }

}

package com.picpaydesafio.demopicpaydesafio.infrastructure.user.mapper;

import com.picpaydesafio.demopicpaydesafio.domain.user.model.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


  public User toDomain(UserEntity entity) {
    return new User(
        entity.getId(),
        entity.getFirstName(),
        entity.getLastName(),
        entity.getDocument(),
        entity.getEmail(),
        entity.getPassword(),
        entity.getBalance(),
        entity.getUserType()
    );
  }

  public UserEntity toEntity(User domain) {
    return new UserEntity(
        domain.getId(),
        domain.getFirstName(),
        domain.getLastName(),
        domain.getDocument(),
        domain.getEmail(),
        domain.getPassword(),
        domain.getBalance(),
        domain.getUserType()
    );
  }
}

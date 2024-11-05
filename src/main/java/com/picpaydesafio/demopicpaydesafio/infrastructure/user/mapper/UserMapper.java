package com.picpaydesafio.demopicpaydesafio.infrastructure.user.mapper;

import com.picpaydesafio.demopicpaydesafio.domain.user.model.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.user.entity.UserEntity;
import com.picpaydesafio.demopicpaydesafio.web.user.dtos.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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

  public UserResponseDTO toResponseDTO(User user) {
    return new UserResponseDTO(
        user.getFirstName(),
        user.getLastName(),
        user.getDocument(),
        user.getBalance(),
        user.getEmail(),
        user.getPassword(),
        user.getUserType()
    );
  }

}

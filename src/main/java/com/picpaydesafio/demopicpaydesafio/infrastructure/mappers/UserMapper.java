package com.picpaydesafio.demopicpaydesafio.infrastructure.mappers;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.UserEntity;
import com.picpaydesafio.demopicpaydesafio.web.dtos.UserResponseDTO;
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
        entity.getUserType(),
        entity.getRole()
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
        domain.getUserType(),
        domain.getRole()
    );
  }

  public UserResponseDTO toResponseDTO(User domain) {
    return new UserResponseDTO(
        domain.getFirstName(),
        domain.getLastName(),
        domain.getDocument(),
        domain.getBalance(),
        domain.getEmail(),
        domain.getPassword(),
        domain.getUserType()
    );
  }

}

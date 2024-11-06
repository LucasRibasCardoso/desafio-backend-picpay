package com.picpaydesafio.demopicpaydesafio.infrastructure.repositories.interfaces;

import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findUserById(Long id);
}

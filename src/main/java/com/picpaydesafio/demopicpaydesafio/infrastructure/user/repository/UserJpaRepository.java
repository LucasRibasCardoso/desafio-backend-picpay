package com.picpaydesafio.demopicpaydesafio.infrastructure.user.repository;

import com.picpaydesafio.demopicpaydesafio.infrastructure.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findUserById(Long id);
}

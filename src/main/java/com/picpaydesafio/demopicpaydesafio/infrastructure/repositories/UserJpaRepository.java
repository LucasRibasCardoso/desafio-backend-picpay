package com.picpaydesafio.demopicpaydesafio.infrastructure.repositories;

import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByDocument(String document);
  Optional<UserEntity> findByEmail(String email);
}

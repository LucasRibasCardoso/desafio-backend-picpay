package com.picpaydesafio.demopicpaydesafio.infrastructure.user.repository;

import com.picpaydesafio.demopicpaydesafio.domain.user.model.User;
import com.picpaydesafio.demopicpaydesafio.domain.user.repository.UserRepository;
import com.picpaydesafio.demopicpaydesafio.infrastructure.user.mapper.UserMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {

  private final UserJpaRepository jpaRepository;
  private final UserMapper mapper;

  @Override
  public Optional<User> findById(Long id) {
    return jpaRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public User save(User user) {
    return mapper.toDomain(jpaRepository.save(mapper.toEntity(user)));
  }

}

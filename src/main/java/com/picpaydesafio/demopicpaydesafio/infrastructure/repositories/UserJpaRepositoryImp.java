package com.picpaydesafio.demopicpaydesafio.infrastructure.repositories;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesInterfaces.UserRepository;
import com.picpaydesafio.demopicpaydesafio.infrastructure.mappers.UserMapper;
import com.picpaydesafio.demopicpaydesafio.infrastructure.repositories.interfaces.UserJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserJpaRepositoryImp implements UserRepository {

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

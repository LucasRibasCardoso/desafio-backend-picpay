package com.picpaydesafio.demopicpaydesafio.infrastructure.repositories.imp;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import com.picpaydesafio.demopicpaydesafio.domain.repositoriesDomain.UserRepository;
import com.picpaydesafio.demopicpaydesafio.infrastructure.mappers.UserMapper;
import com.picpaydesafio.demopicpaydesafio.infrastructure.repositories.UserJpaRepository;
import java.util.List;
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

  @Override
  public Optional<User> findByDocument(String document) {
    return jpaRepository.findByDocument(document).map(mapper::toDomain);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return jpaRepository.findByEmail(email).map(mapper::toDomain);
  }

  @Override
  public List<User> findAll() {
    return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
  }

}

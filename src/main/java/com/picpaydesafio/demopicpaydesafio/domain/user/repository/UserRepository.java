package com.picpaydesafio.demopicpaydesafio.domain.user.repository;

import com.picpaydesafio.demopicpaydesafio.domain.user.model.User;
import java.util.Optional;

public interface UserRepository {
  Optional<User> findById(Long id);
  User save(User user);
}

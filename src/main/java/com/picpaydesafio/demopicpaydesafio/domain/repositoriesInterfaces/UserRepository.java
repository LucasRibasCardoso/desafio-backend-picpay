package com.picpaydesafio.demopicpaydesafio.domain.repositoriesInterfaces;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import java.util.Optional;

public interface UserRepository {
  Optional<User> findById(Long id);
  User save(User user);
}

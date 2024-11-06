package com.picpaydesafio.demopicpaydesafio.domain.repositoriesInterfaces;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
  User save(User user);
  Optional<User> findById(Long id);
  Optional<User> findByDocument(String document);
  Optional<User> findByEmail(String email);
  List<User> findAll();
}

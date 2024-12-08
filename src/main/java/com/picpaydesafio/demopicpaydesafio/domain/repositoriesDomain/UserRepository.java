package com.picpaydesafio.demopicpaydesafio.domain.repositoriesDomain;

import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository {
  User save(User user);
  Optional<User> findById(Long id);
  Optional<User> findByDocument(String document);
  Optional<User> findByEmail(String email);
  List<User> findAll();
}

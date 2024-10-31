package com.picpaydesafio.demopicpaydesafio.repositories;

import com.picpaydesafio.demopicpaydesafio.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByDocument(String document);

  Optional<User> findUserById(Long id);
}

package com.picpaydesafio.demopicpaydesafio.configs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.picpaydesafio.demopicpaydesafio.configs.security.exeptions.GenerationTokenException;
import com.picpaydesafio.demopicpaydesafio.domain.models.User;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secreet;

  public String generateToken(User user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secreet);
      String token = JWT.create()
          .withIssuer("auth-api")
          .withSubject(user.getEmail())
          .withClaim("role", user.getRole().toString())
          .withExpiresAt(generateExpirationDate())
          .sign(algorithm);

      return token;
    }
    catch (JWTCreationException exception) {
      throw new GenerationTokenException("Erro enquanto gerava o token JWT.");
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secreet);
      return JWT.require(algorithm)
          .withIssuer("auth-api")
          .build()
          .verify(token)
          .getSubject();
    }
    catch (JWTCreationException exception) {
      return "";
    }

  }

  private Instant generateExpirationDate() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}

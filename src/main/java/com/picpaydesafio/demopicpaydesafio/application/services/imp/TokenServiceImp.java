package com.picpaydesafio.demopicpaydesafio.application.services.imp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.CustomTokenExpiredException;
import com.picpaydesafio.demopicpaydesafio.application.exceptions.InvalidTokenException;
import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.enums.UserRole;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken(String email, UserRole role) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
          .withIssuer("auth-api")
          .withSubject(email)
          .withClaim("role", role.toString())
          .withIssuedAt(generateDateFromToken(0)) // data atual
          .withExpiresAt(generateDateFromToken(60)) // data atual + 60 minutos
          .sign(algorithm);
    } catch (Exception e) {
      throw new RuntimeException("Erro ao gerar o token JWT.", e);
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      var verifier = JWT.require(algorithm)
          .withIssuer("auth-api")
          .build();

      var decodedJWT = verifier.verify(token);
      String subject = decodedJWT.getSubject();

      if (subject == null) {
        throw new InvalidTokenException("O token JWT não possui um subject válido.");
      }

      return subject;
    }
    catch (TokenExpiredException exception) {
      throw new CustomTokenExpiredException("O token JWT está invalido. Faça login novamente para renova-lo.");
    }
    catch (JWTVerificationException exception) {
      throw new InvalidTokenException("Token JWT inválido. Motivo: " + exception.getMessage());
    }
  }

  private Date generateDateFromToken(int minutes) {
    return Date.from(LocalDateTime.now()
        .plusMinutes(minutes)
        .toInstant(ZoneOffset.of("-03:00")));
  }
}

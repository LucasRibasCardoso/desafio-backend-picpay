package com.picpaydesafio.demopicpaydesafio.configs.security;

import com.picpaydesafio.demopicpaydesafio.application.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final SecurityFilter securityFilter;
  private final UserService userService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize

            // Endpoint para acessar o banco de dados
            .requestMatchers("/h2-console/**").permitAll()

            // Endpoints de autenticação e cadastro de usuários
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

            // Endpoint para listagem de todos os usuários (apenas para ADMIN)
            .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")

            // Endpoint de POST para transações (apenas o próprio usuário pode realizar)
            .requestMatchers(HttpMethod.POST, "/api/transactions").authenticated()

            // Endpoint de GET para listar transações do usuário logado
            .requestMatchers(HttpMethod.GET, "/api/transactions").authenticated()

            // Todas as outras requisições devem ser autenticadas
            .anyRequest().authenticated()
        )
        .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder =
        http.getSharedObject(AuthenticationManagerBuilder.class);

    authenticationManagerBuilder
        .userDetailsService(userService::findUserByEmail)
        .passwordEncoder(passwordEncoder());

    return authenticationManagerBuilder.build();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

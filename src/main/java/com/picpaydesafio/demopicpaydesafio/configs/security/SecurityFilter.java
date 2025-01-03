package com.picpaydesafio.demopicpaydesafio.configs.security;


import com.picpaydesafio.demopicpaydesafio.application.services.TokenService;
import com.picpaydesafio.demopicpaydesafio.application.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final UserService userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
  ) throws ServletException, IOException {

    var token = this.recoverToken(request);

    if (token != null) {
      String emailLogin = tokenService.validateToken(token);
      UserDetails user = userService.findUserByEmail(emailLogin);

      var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return null;
    }
    return authHeader.replace("Bearer ", "");
  }
}

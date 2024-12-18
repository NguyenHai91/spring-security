package com.hainguyen.security.security.jwt;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.hainguyen.security.exception.AppExceptionHandler;
import com.hainguyen.security.exception.CustomException;
import com.hainguyen.security.model.Token;
import com.hainguyen.security.service.BaseRedisService;
import com.hainguyen.security.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtAuthFilter extends OncePerRequestFilter{
  @Value("app.jwt.secret_key")
  private String SECRET_KEY;

  @Autowired
  private JwtTokenUtils jwtTokenUtils;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private BaseRedisService redisService;
  
  @Autowired
  private AppExceptionHandler appExceptionHandler;

  private final HandlerExceptionResolver handlerResolver;

  public JwtAuthFilter(HandlerExceptionResolver handlerExceptionResolver) {
    this.handlerResolver = handlerExceptionResolver;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if (!jwtTokenUtils.hasAuthorization(request)) {
      filterChain.doFilter(request, response);
      return;
    }
    String token = jwtTokenUtils.getToken(request);

    Token tokenRecord = (Token) redisService.hashGet(token, "token");
    if (Objects.isNull(tokenRecord)) {
      tokenRecord = tokenService.getByToken(token);
    }

    if (Objects.isNull(tokenRecord)) {
      filterChain.doFilter(request, response);
      throw new CustomException("You're not authenticated");
    }
    if (!tokenRecord.isStatus()) {
      filterChain.doFilter(request, response);
      throw new CustomException("Token invalid");
    }

    try {
      if (jwtTokenUtils.verifyToken(token)) {
        jwtTokenUtils.setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
      }
    } catch (Exception ex) {
      this.handlerResolver.resolveException(request, response, appExceptionHandler, ex);
    }
    filterChain.doFilter(request, response);
  }

}

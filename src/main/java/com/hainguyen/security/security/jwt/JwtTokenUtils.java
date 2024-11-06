package com.hainguyen.security.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.hainguyen.security.exception.CustomException;
import com.hainguyen.security.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenUtils {
  @Value("${app.jwt.valid_time_token}")
  private  long VALID_TIME_TOKEN;
  @Value("${app.jwt.valid_time_refresh_token}")
  private long VALID_TIME_REFRESH_TOKEN;

  @Value("${app.jwt.valid_time_reset_key}")
  private long VALID_TIME_RESET_KEY;

  @Value("${app.jwt.token_prefix}")
  private String TOKEN_PREFIX;
  @Value("${app.jwt.secret_key}")
  private String SECRET_KEY;

  @Autowired
  private UserDetailsService customUserDetailsService;

  public String createToken(User user) {
    return this.generateToken(user, VALID_TIME_TOKEN);
  }

  public String createRefreshToken(User user) {
    return this.generateToken(user, VALID_TIME_REFRESH_TOKEN);
  }

  public String createResetKey(User user) {
    return this.generateToken(user, VALID_TIME_RESET_KEY);
  }

  public String generateToken(User user, long expiredTime) {
    if (user == null) {
      return null;
    }

    return Jwts.builder()
        .setSubject(user.getEmail())
        .claim("roles", user.getRoles().toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  public boolean hasAuthorization(HttpServletRequest request) {
    return request.getHeader(HttpHeaders.AUTHORIZATION) != null;
  }

  public String getToken(HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (token == null || !token.startsWith(TOKEN_PREFIX)) {
      throw new CustomException("User not authenticated");
    }
    token = token.substring(TOKEN_PREFIX.length()).trim();
    return token;
  }

  public Claims parseClaims(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
  }

  public boolean verifyToken(String token) throws Exception {
    try {
      Jwts.parser()
          .setSigningKey(SECRET_KEY)
          .parseClaimsJws(token)
          .getBody();
      return true;
    } catch (ExpiredJwtException ex) {
      System.out.println("JWT token expired");
      throw new CustomException("JWT token expired");
    } catch (SignatureException ex) {
      System.out.println("Signature validation invalid: " + ex.getMessage());
      throw new CustomException("Signature validation invalid");
    } catch (IllegalArgumentException ex) {
      System.out.println("JWT claims string is empty or null");
      throw new CustomException("JWT claims string is empty or null");
    } catch (MalformedJwtException ex) {
      System.out.println("Invalid JWT token");
      throw new CustomException("Invalid JWT token");
    } catch (UnsupportedJwtException ex) {
      System.out.println("Unsupported JWT token");
      throw new CustomException("Unsupported JWT token");
    }
    
  }

  public void setAuthenticationContext(String token, HttpServletRequest request) {
    Claims claims = this.parseClaims(token);
    String subject = claims.getSubject();
    if (subject == null || subject.isBlank()) {
      return;
    }
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(subject);
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}

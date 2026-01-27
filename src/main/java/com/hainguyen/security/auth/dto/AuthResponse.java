package com.hainguyen.security.auth.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {
  private String username;

  private String token;

  private String refreshToken;
  
  private boolean isAuthenticated;
}

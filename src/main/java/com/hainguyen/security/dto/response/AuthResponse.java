package com.hainguyen.security.dto.response;

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

package com.hainguyen.security.auth.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthRequest {
  private String email;

  private String password;
}

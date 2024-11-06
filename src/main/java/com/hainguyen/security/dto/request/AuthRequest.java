package com.hainguyen.security.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthRequest {
  private String email;

  private String password;
}

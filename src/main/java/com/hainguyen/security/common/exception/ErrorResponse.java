package com.hainguyen.security.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ErrorResponse {
  private final int status;
  
  private String message;
}

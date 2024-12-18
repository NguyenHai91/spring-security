package com.hainguyen.security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    private final int status;

    private String message;
    
    private T data;
}

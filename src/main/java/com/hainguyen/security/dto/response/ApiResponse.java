package com.hainguyen.security.dto.response;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    private final int status;

    private String message;
    
    private T data;
}

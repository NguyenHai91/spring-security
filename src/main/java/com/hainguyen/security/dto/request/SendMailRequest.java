package com.hainguyen.security.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SendMailRequest {
    @NotNull
    private String recipients;
    @NotNull
    private String subject;
    @NotNull
    private String content;
    
    private MultipartFile[] files;
}

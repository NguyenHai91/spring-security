package com.hainguyen.security.service;

import java.io.UnsupportedEncodingException;

import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;

public interface MailService {

    public String sendMail(String recipients, String Subject, String content, MultipartFile[] files) throws MessagingException, UnsupportedEncodingException;
}

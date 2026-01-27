package com.hainguyen.security.common.sendMail;

import java.io.UnsupportedEncodingException;

import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;

public interface MailService {
    public boolean checkCodeUser(Long idUser, String code);
    public void sendConfirmLinkToEmail(String email, Long idUser, String code) throws MessagingException, UnsupportedEncodingException;
    public String sendMail(String recipients, String Subject, String content, MultipartFile[] files) throws MessagingException, UnsupportedEncodingException;
}

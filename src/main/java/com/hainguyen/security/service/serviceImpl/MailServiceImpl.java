package com.hainguyen.security.service.serviceImpl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.hainguyen.security.exception.CustomException;
import com.hainguyen.security.model.Token;
import com.hainguyen.security.service.MailService;
import com.hainguyen.security.service.TokenService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private TokenService tokenService;

    @Value("${app.mail.emailFrom}")
    private String emailFrom;

    @Override
    public boolean checkCodeUser(Long idUser, String code) {
        Token tokenRecord = tokenService.getByToken(code);
        if (tokenRecord != null) {
            throw new CustomException("Confirm failed, try again");
        }
        if (tokenRecord.getId() != idUser) {
            throw new CustomException("Can not found id user");
        }
        if (tokenRecord.getToken() != code) {
            throw new CustomException("Code invalid");
        }
        return true;
    }

    @Override
    public void sendConfirmLinkToEmail(String emailTo, Long idUser, String code) throws MessagingException, UnsupportedEncodingException {
        log.info("Sending email confirm");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        
        Context context = new Context();
        String linkConfirm = "/users/" + idUser +"/?secretCode=" + code;

        context.setVariable("link", linkConfirm);
        context.setVariable("emailTo", emailTo);
        context.setVariable("emailFrom", emailFrom);;

        helper.setFrom(emailFrom, "Admin");
        helper.setTo(emailTo);
        helper.setSubject("Confirm your account");

        String html = templateEngine.process("confirm-email.html", context);
        helper.setText(html, true);
        mailSender.send(message);

        log.info("Email sent");
    }

    @Override
    public String sendMail(String recipients, String subject, String content, MultipartFile[] files) throws MessagingException, UnsupportedEncodingException {
        log.info("Sending email...");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
        helper.setFrom(emailFrom, "Admin");

        if (recipients.contains(",")) {
            helper.setTo(InternetAddress.parse(recipients));
        } else {
            helper.setTo(recipients);
        }

        if (files != null) {
            for (MultipartFile file: files) {
                helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
            }
        }

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);

        log.info("Email has been send success");
        return "sent";
    }
    
}

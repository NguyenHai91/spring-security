package com.hainguyen.security.config.i18n;


import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class LocaleResolver extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        List<Locale> locales = List.of(
            new Locale("en"), 
            new Locale("vi")
        );
        String languageHeader = request.getHeader("Accept-Language");
        if (StringUtils.hasLength(languageHeader)) {
            return Locale.lookup(Locale.LanguageRange.parse(languageHeader), locales);
        }
        return Locale.getDefault();
    }

    @Bean
    public ResourceBundleMessageSource bundleMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(new Locale("en"));
        messageSource.setCacheSeconds(3600);
        return messageSource;
    }
}
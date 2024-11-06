package com.hainguyen.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.hainguyen.security.security.jwt.JwtAuthFilter;
import com.hainguyen.security.security.oauth2.JWTtoUserConvertor;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  UserDetailsService customUserDetailsService() {
    return new CustomUserDetailsService();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver exceptionResolver;

  @Bean
  DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(customUserDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public JwtAuthFilter jwtAuthFilter() {
    return new JwtAuthFilter(exceptionResolver);
  }

  @Autowired
  public JWTtoUserConvertor jWtToUserConvertor;

  // @Bean
  // public WebMvcConfigurer corsConfigurer() {
  //   return new WebMvcConfigurer() {
  //     public void addCorsMappings(CorsRegistry registry) {
  //       registry.addMapping("**")
  //               .allowedOrigins("http://localhost:3000")
  //               .allowCredentials(false)
  //               .allowedHeaders("*")
  //               .maxAge(3600);
  //     }
  //   };
  // }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    String[] endpoints = {"/login", "/api/**"};
    http.csrf(AbstractHttpConfigurer::disable)
      .cors(cors -> cors.disable())
      .authorizeHttpRequests(
      request -> request
        .requestMatchers(endpoints).permitAll()
        .anyRequest().permitAll()
      )
      .formLogin(login -> login.loginProcessingUrl("/login").defaultSuccessUrl("/"))
      .httpBasic(Customizer.withDefaults())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }
  

  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers("/static/js/**", "/static/css/**");
  }


}

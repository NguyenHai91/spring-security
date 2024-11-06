package com.hainguyen.security.exception;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class AppExceptionHandler extends RuntimeException {
  @ExceptionHandler(NotFoundException.class)
  public ErrorResponse notFoundException(NotFoundException ex, HttpServletRequest request) {
    String message = ex.getMessage();
    int start = message.lastIndexOf("[");
    int end = message.lastIndexOf("]");
    message = message.substring(start + 1, end - 1);
    return new ErrorResponse(HttpStatus.NOT_FOUND.value(), message);
  }

  @ExceptionHandler(CustomException.class)
  public ErrorResponse customExceptionHandler(CustomException ex, HttpServletRequest request) {
    return new ErrorResponse(
      HttpStatus.BAD_REQUEST.value(), 
      ex.getMessage()
    );
  }

  @ExceptionHandler(Exception.class)
  public ErrorResponse exceptionHandler(Exception ex, HttpServletRequest request) {
    return new ErrorResponse(
      HttpStatus.BAD_REQUEST.value(), 
      ex.getMessage()
    );
  }



}

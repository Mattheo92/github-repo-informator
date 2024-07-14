package com.Mattheo992.githubRepoInformator.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(ResponseStatusException.class)
public ResponseEntity<String> handleIllegalArgumentException(ResponseStatusException e){
    return ResponseEntity.status(e.getStatusCode()).body(e.getReason());}
}
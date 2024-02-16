package com.example.demoredis.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * application.yml 파일 설정
 * spring:
 * mvc:
 * throw-exception-if-no-handler-found: true
 * web:
 * resources:
 * add-mappings: false
 */
@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 Not Found 오류시 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNotFoundError(NoHandlerFoundException ex) {
        log.info("uri:{}, message:{}", ex.getRequestURL(), ex.getMessage());
        return new ResponseEntity<>("해당 API 경로를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}

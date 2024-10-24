package org.askforai._core.exception;

import org.askforai._core.exception.custom.Exception400;
import org.askforai._core.exception.custom.Exception401;
import org.askforai._core.exception.custom.Exception403;
import org.askforai._core.exception.custom.Exception404;
import org.askforai._core.exception.custom.Exception500;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<String> ex400(Exception400 ex) {
        log.warn("400 오류 발생: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    
    @ExceptionHandler(Exception401.class)
    public ResponseEntity<String> ex401(Exception401 ex) {
        log.warn("401 오류 발생: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage());
    }
    
    @ExceptionHandler(Exception403.class)
    public ResponseEntity<String> ex403(Exception403 ex) {
        log.warn("403 오류 발생: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage());
    }
    
    @ExceptionHandler(Exception404.class)
    public ResponseEntity<String> ex404(Exception404 ex) {
        log.warn("404 오류 발생: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    
    @ExceptionHandler(Exception500.class)
    public ResponseEntity<String> ex500(Exception500 ex) {
        log.error("서버 오류 발생: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버 오류가 발생했습니다: " + ex.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        log.error("예기치 않은 오류 발생: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("예기치 않은 오류가 발생했습니다.: " + ex.getMessage());
    }
}

package org.askforai._core.exception;

import org.askforai._core.exception.custom.Exception400;
import org.askforai._core.exception.custom.Exception401;
import org.askforai._core.exception.custom.Exception403;
import org.askforai._core.exception.custom.Exception404;
import org.askforai._core.exception.custom.Exception409;
import org.askforai._core.exception.custom.Exception500;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	private ResponseEntity<String> handleException(HttpStatus status, Exception ex) {
        return ResponseEntity
                .status(status)
                .body(ex.getMessage());
    }
	private ResponseEntity<String> handleException(HttpStatus status, String msg) {
        return ResponseEntity
                .status(status)
                .body(msg);
    }
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    StringBuilder errorMessage = new StringBuilder("유효성 검사 실패: ");
	    
	    ex.getBindingResult().getFieldErrors().forEach(error -> {
	        errorMessage.append(error.getField())
	                    .append(": ")
	                    .append(error.getDefaultMessage())
	                    .append("; ");
	    });
	    
	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(errorMessage.toString());
	}

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<String> ex400(Exception400 ex) {
    	return handleException(HttpStatus.BAD_REQUEST, ex);
    }
    
    @ExceptionHandler(Exception401.class)
    public ResponseEntity<String> ex401(Exception401 ex) {
    	return handleException(HttpStatus.UNAUTHORIZED, ex);
    }
    
    @ExceptionHandler(Exception403.class)
    public ResponseEntity<String> ex403(Exception403 ex) {
    	return handleException(HttpStatus.FORBIDDEN, ex);
    }
    
    @ExceptionHandler(Exception404.class)
    public ResponseEntity<String> ex404(Exception404 ex) {
    	return handleException(HttpStatus.NOT_FOUND, ex);
    }
    
    @ExceptionHandler(Exception409.class)
    public ResponseEntity<String> ex409(Exception409 ex) {
        return handleException(HttpStatus.CONFLICT, ex);
    }
    
    @ExceptionHandler(Exception500.class)
    public ResponseEntity<String> ex500(Exception500 ex) {
    	return handleException(HttpStatus.INTERNAL_SERVER_ERROR, "서버오류!!!");
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> ex(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("예기치 않은 오류가 발생했습니다.");
    }
    
    
}

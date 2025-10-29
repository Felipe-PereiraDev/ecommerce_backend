package br.com.felipedev.ecommerce.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(DescriptionExistsException.class)
    public ResponseEntity<ErrorResponse> handleDescriptionExistsException(Exception ex, HttpServletRequest servletRequest) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex, servletRequest);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(Exception ex, HttpServletRequest servletRequest) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex, servletRequest);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, Exception exception, HttpServletRequest servletRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage(),
                servletRequest.getRequestURI(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(errorResponse);
    }
}

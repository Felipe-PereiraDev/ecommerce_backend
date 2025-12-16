package br.com.felipedev.ecommerce.exception;

import br.com.felipedev.ecommerce.dto.error.ApiError;
import br.com.felipedev.ecommerce.dto.error.ErrorResponse;
import br.com.felipedev.ecommerce.dto.error.FieldError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(Exception ex, HttpServletRequest servletRequest) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex, servletRequest);
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ErrorResponse> handleInvalidLoginException (Exception ex, HttpServletRequest servletRequest) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex, servletRequest);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ErrorResponse> handleUnprocessableEntityException (Exception ex, HttpServletRequest servletRequest) {
        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex, servletRequest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException (MethodArgumentNotValidException ex, HttpServletRequest servletRequest) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Falha na validação",
                servletRequest.getRequestURI(),
                LocalDateTime.now()
        );
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> apiError.addFieldError(new FieldError(error.getField(), error.getDefaultMessage())));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
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

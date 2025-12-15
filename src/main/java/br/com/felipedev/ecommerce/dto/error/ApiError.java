package br.com.felipedev.ecommerce.dto.error;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ApiError extends ErrorResponse{
    private List<FieldError> fieldErrorList = new ArrayList<>();

    public ApiError(int status, String error, String message, String path, LocalDateTime timestamp) {
        super(status, error, message, path, timestamp);
    }

    public void addFieldError(FieldError fieldError) {
        fieldErrorList.add(fieldError);
    }
}

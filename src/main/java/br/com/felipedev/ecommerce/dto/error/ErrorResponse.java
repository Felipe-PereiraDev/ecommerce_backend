package br.com.felipedev.ecommerce.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
        private int status;
        private String error;
        private String message;
        private String path;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
        private LocalDateTime timestamp;

    public ErrorResponse(int status,
                               String error,
                               String message,
                               String path,
                               LocalDateTime timestamp ) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
    }
}

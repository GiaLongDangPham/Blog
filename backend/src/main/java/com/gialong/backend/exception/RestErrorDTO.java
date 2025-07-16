package com.gialong.backend.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class RestErrorDTO {
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime timestamp;

    private String message;

    public RestErrorDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public RestErrorDTO(HttpStatus status) {
        this();
        this.status = status;
    }

    public RestErrorDTO(HttpStatus status, Throwable throwable) {
        this();
        this.status = status;
        this.message = "Unexpected Error";
    }

    public RestErrorDTO(HttpStatus status, String message, Throwable throwable) {
        this();
        this.status = status;
        this.message = message;
    }
}

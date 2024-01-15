package com.academy.enroll.configurations.Execptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionResponse {
    private LocalDateTime date;
    private Integer httpStatusCode;
    private HttpStatus httpStatus;
    private Object message;
}

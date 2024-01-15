package com.academy.enroll.configurations.Execptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Order(-2)
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {AppRuntimeException.class})
    public ResponseEntity<?> automaticResponseExceptionHandler(AppRuntimeException exception) {
        return genericResponseExceptionHandler(exception);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<?> automaticResponseExceptionHandler(IllegalArgumentException exception) {
        return genericResponseExceptionHandler(exception);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<?> automaticResponseExceptionHandler(HttpMessageNotReadableException exception) {
        return genericResponseExceptionHandler(exception);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<?> automaticResponseExceptionHandler(NullPointerException exception) {
        return genericResponseExceptionHandler(exception);
    }

    @ExceptionHandler(value = {SecurityException.class})
    public ResponseEntity<?> automaticResponseExceptionHandler(SecurityException exception) {
        return genericResponseExceptionHandler(exception);
    }

    @ExceptionHandler(value = {WebExchangeBindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> automaticResponseExceptionHandler(WebExchangeBindException exception) {
        log.error(exception.getMessage(), exception);
        Map<String, String> mapErrors = new HashMap<>();
        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(oError ->
                        mapErrors.put(
                                oError.getField(),
                                oError.getDefaultMessage()));
        return new ResponseEntity<>(
                buildExceptionResponse(mapErrors),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> automaticResponseExceptionHandler(Exception ex) {
        log.info("Exception type : " + ex.getClass().getName());
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error 0000");
    }


    private ResponseEntity<?> genericResponseExceptionHandler(Throwable exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(
                buildExceptionResponse(exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    private ExceptionResponse buildExceptionResponse(Object errorObject) {
        log.error("Build exception response");
        return ExceptionResponse
                .builder()
                .date(LocalDateTime.now())
                .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(errorObject)
                .build();
    }
}

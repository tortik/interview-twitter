package com.hsbc.interviewtwitter.common.rest;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.time.ZonedDateTime;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @Order(1)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiBaseInfoError> validationException(ServerHttpRequest request, ConstraintViolationException ex) {
        ApiBaseInfoError baseInfo = ApiBaseInfoError.of(request.getURI(), "Validation issue", ex.getMessage());
        log.error("Got exception - " + baseInfo, ex);
        return ResponseEntity.badRequest().body(baseInfo);
    }

    @Order
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiBaseInfoError> baseException(ServerHttpRequest request, Exception ex) {
        ApiBaseInfoError baseInfo = ApiBaseInfoError.of(request.getURI(), "Validation issue", ex.getMessage());
        log.error("Got exception - " + baseInfo, ex);
        return ResponseEntity.badRequest().body(baseInfo);
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    static class ApiBaseInfoError {
        private ZonedDateTime timestamp = ZonedDateTime.now();
        @NonNull
        private URI path;
        @NonNull
        private String reason;
        @NonNull
        private String message;

    }

}

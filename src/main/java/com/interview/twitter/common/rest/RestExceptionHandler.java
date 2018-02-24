package com.interview.twitter.common.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interview.twitter.common.exception.ResourceNotFoundException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.core.Ordered;
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

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiBaseInfoError> validationException(ServerHttpRequest request, ConstraintViolationException ex) {
        ApiBaseInfoError baseInfo = ApiBaseInfoError.of(request.getURI(), "Validation issue", ex.getMessage());
        log.error("Got exception - " + baseInfo, ex);
        return ResponseEntity.badRequest().body(baseInfo);
    }

    @ResponseStatus(code = HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<ApiBaseInfoError> notImplementedException(ServerHttpRequest request, NotImplementedException ex) {
        ApiBaseInfoError baseInfo = ApiBaseInfoError.of(request.getURI(), "Endpoint not implemented", ex.getMessage());
        log.error("Got exception - " + baseInfo, ex);
        return ResponseEntity.badRequest().body(baseInfo);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiBaseInfoError> resourceNotFoundException(ServerHttpRequest request, ResourceNotFoundException ex) {
        ApiBaseInfoError baseInfo = ApiBaseInfoError.of(request.getURI(), "Not Found", ex.getMessage());
        log.error("Got exception - " + baseInfo, ex);
        return ResponseEntity.badRequest().body(baseInfo);
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiBaseInfoError> baseException(ServerHttpRequest request, Exception ex) {
        ApiBaseInfoError baseInfo = ApiBaseInfoError.of(request.getURI(), "Server error", ex.getMessage());
        log.error("Got exception - " + baseInfo, ex);
        return ResponseEntity.badRequest().body(baseInfo);
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    static class ApiBaseInfoError {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        private ZonedDateTime timestamp = ZonedDateTime.now();
        @NonNull
        private URI path;
        @NonNull
        private String reason;
        @NonNull
        private String message;

    }

}

package io.labs64.ecommerce.exception;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.labs64.ecommerce.v1.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest request) {
        String traceId = request.getHeader("X-Request-ID");
        ErrorResponse error = new ErrorResponse(ex.getErrorCode().name(), ex.getMessage());
        error.setTraceId(traceId);
        error.setTimestamp(OffsetDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Optional: catch-all
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(Exception ex, HttpServletRequest request) {
        String traceId = request.getHeader("X-Request-ID");
        ErrorResponse error = new ErrorResponse(ErrorCode.INTERNAL_ERROR.name(), "Unexpected error");
        error.setTraceId(traceId);
        error.setTimestamp(OffsetDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
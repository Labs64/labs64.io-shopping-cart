package io.labs64.cart.exception;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.labs64.cart.v1.model.ErrorCode;
import io.labs64.cart.v1.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(final NotFoundException ex, final HttpServletRequest request) {
        ErrorResponse error = buildErrorResponse(ex.getErrorCode(), ex.getMessage(), request);

        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(final MethodArgumentNotValidException ex,
            final HttpServletRequest request) {
        String errorMessage = "Validation failed";

        FieldError firstError = ex.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);

        if (firstError != null) {
            errorMessage = String.format("Field '%s' is invalid: %s", firstError.getField(),
                    firstError.getDefaultMessage());
        }

        ErrorResponse error = buildErrorResponse(ErrorCode.VALIDATION_ERROR, errorMessage, request);

        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleCustomValidation(final ValidationException ex,
            final HttpServletRequest request) {
        String errorMessage = String.format("Field '%s' is invalid: %s", ex.getField(), ex.getMessage());

        ErrorResponse error = buildErrorResponse(ex.getErrorCode(), errorMessage, request);

        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(final Exception ex, final HttpServletRequest request) {
        ErrorResponse error = buildErrorResponse(ErrorCode.INTERNAL_ERROR, "Unexpected error", request);

        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private ErrorResponse buildErrorResponse(ErrorCode code, String message, HttpServletRequest request) {
        String traceId = request.getHeader("X-Request-ID");

        ErrorResponse error = new ErrorResponse();
        error.setCode(code);
        error.setMessage(message);
        error.setTraceId(traceId);
        error.setTimestamp(OffsetDateTime.now());

        return error;
    }
}
package io.labs64.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.labs64.ecommerce.v1.model.ErrorCode;
import io.labs64.ecommerce.v1.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(final NotFoundException ex, final HttpServletRequest request) {
        String traceId = request.getHeader("X-Request-ID");
        ErrorResponse error = new ErrorResponse();
        error.setCode(ex.getErrorCode());
        error.setTraceId(traceId);
        error.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(final MethodArgumentNotValidException ex,
            final HttpServletRequest request) {
        String traceId = request.getHeader("X-Request-ID");
        String errorMessage = "Validation failed";

        FieldError firstError = ex.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);

        if (firstError != null) {
            errorMessage = String.format("Field '%s' is invalid: %s", firstError.getField(),
                    firstError.getDefaultMessage());
        }

        ErrorResponse error = new ErrorResponse();
        error.setCode(ErrorCode.VALIDATION_ERROR);
        error.setMessage(errorMessage);
        error.setTraceId(traceId);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(final Exception ex, final HttpServletRequest request) {
        String traceId = request.getHeader("X-Request-ID");
        ErrorResponse error = new ErrorResponse();
        error.setCode(ErrorCode.INTERNAL_ERROR);
        error.setMessage("Unexpected error");
        error.setTraceId(traceId);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
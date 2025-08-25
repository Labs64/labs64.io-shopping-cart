package io.labs64.cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.labs64.cart.v1.model.ErrorCode;
import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ValidationException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String field;

    public ValidationException(String field, String message) {
        super(message);
        this.field = field;
        this.errorCode = ErrorCode.VALIDATION_ERROR;
    }
}

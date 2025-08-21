package io.labs64.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.labs64.ecommerce.v1.model.ErrorCode;
import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public NotFoundException(String message) {
        super(message);
        this.errorCode = ErrorCode.NOT_FOUND;
    }
}

package io.labs64.ecommerce.v1.dto;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private OffsetDateTime timestamp;
    private String traceId;

    public ErrorResponse(final String code, final String message, final String traceId) {
        this.code = code;
        this.message = message;
        this.traceId = traceId;
        this.timestamp = OffsetDateTime.now();
    }
}

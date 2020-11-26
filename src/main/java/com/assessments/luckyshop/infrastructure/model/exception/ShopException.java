package com.assessments.luckyshop.infrastructure.model.exception;

import com.assessments.luckyshop.infrastructure.model.ApplicationErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ShopException extends RuntimeException {
    @Getter
    private final HttpStatus httpStatus;

    public ShopException(ApplicationErrorCode applicationErrorCode) {
        super(applicationErrorCode.getMessage());
        this.httpStatus = applicationErrorCode.getHttpStatus();
    }

    public ShopException(ApplicationErrorCode applicationErrorCode, Object... params) {
        super(String.format(applicationErrorCode.getMessage(), params));
        this.httpStatus = applicationErrorCode.getHttpStatus();
    }
}

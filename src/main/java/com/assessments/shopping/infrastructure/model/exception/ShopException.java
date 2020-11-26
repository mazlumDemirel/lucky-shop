package com.assessments.shopping.infrastructure.model.exception;

import com.assessments.shopping.infrastructure.model.ApplicationErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ShopException extends RuntimeException {
    @Getter
    private final HttpStatus httpStatus;

    public ShopException(ApplicationErrorCode applicationErrorCode) {
        super(applicationErrorCode.getMessage());
        this.httpStatus = applicationErrorCode.getHttpStatus();
    }
}

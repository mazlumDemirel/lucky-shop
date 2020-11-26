package com.assessments.luckyshop.infrastructure.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ApplicationErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "Requested source not found."),
    UN_RESOLVABLE_ENUM_VALUE(HttpStatus.INTERNAL_SERVER_ERROR, "Unable parse %s as %s."),
    UNIQUE_CONSTRAINT_VIOLATION(HttpStatus.BAD_REQUEST, "%s already exists on database.");

    @Getter
    private final HttpStatus httpStatus;

    @Getter
    private final String message;

}

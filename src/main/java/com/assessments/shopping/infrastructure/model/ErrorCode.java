package com.assessments.shopping.infrastructure.model;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getHttpStatus();
}
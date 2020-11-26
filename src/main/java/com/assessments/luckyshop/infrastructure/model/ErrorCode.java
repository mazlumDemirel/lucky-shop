package com.assessments.luckyshop.infrastructure.model;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getHttpStatus();
}
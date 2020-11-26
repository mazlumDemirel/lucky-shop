package com.assessments.shopping.infrastructure.handler;

import com.assessments.shopping.api.dto.response.ApiErrorDTO;
import com.assessments.shopping.infrastructure.model.exception.ShopException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ShopException.class)
    protected ResponseEntity<Object> handleShopException(ShopException ex) {
        logger.error("Handled exception occurred.", ex);
        ApiErrorDTO apiError = new ApiErrorDTO();
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError, ex.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Handled exception occurred.", ex);
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiErrorDTO, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiErrorDTO apiError, HttpStatus httpStatus) {
        return new ResponseEntity<>(apiError, httpStatus);
    }
}

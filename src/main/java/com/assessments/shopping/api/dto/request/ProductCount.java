package com.assessments.shopping.api.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class ProductCount {
    @NotBlank
    private final String productId;
    @Min(1L)
    private final long quantity;
}

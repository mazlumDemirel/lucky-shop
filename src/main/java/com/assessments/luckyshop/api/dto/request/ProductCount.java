package com.assessments.luckyshop.api.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ProductCount {
    @NotBlank
    private String productId;
    @Min(1L)
    private long quantity;
}

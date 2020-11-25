package com.assessments.luckyshop.api.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CreateBillRequest {
    @NotBlank
    private String userId;
    @Min(1L)
    private long quantity;
}

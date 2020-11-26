package com.assessments.shopping.api.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class CreateBillRequest {
    @NotBlank
    private String userId;
    @NotNull
    @Size(min = 1)
    @Valid
    private List<ProductCount> products;
}

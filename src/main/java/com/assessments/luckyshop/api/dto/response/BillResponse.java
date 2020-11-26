package com.assessments.luckyshop.api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class BillResponse {
    private final BigDecimal amount;
    private final BigDecimal discountAmount;
}

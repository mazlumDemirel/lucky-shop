package com.assessments.luckyshop.api.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateBillResponse {
    private BigDecimal amount;
}

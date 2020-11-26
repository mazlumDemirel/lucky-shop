package com.assessments.luckyshop.discount.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Discount {
    AMOUNT(DiscountType.AMOUNT, 5),
    AFFILIATE(DiscountType.PERCENTAGE, 10),
    EMPLOYEE(DiscountType.PERCENTAGE, 30),
    LOYALTY(DiscountType.PERCENTAGE, 5);

    private final DiscountType discountType;
    private final int discountAmount;
}

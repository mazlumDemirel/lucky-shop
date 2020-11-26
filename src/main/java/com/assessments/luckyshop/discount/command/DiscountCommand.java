package com.assessments.luckyshop.discount.command;

import com.assessments.luckyshop.discount.model.enums.DiscountSetting;

import java.math.BigDecimal;

public interface DiscountCommand {
    boolean isApplicable();

    BigDecimal execute(BigDecimal discountAmount);

    DiscountSetting getDiscountSetting();
}

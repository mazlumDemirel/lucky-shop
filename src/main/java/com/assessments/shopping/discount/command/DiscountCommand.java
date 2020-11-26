package com.assessments.shopping.discount.command;

import com.assessments.shopping.discount.model.enums.DiscountSetting;

import java.math.BigDecimal;

public interface DiscountCommand {
    boolean isApplicable();

    BigDecimal execute(BigDecimal discountAmount);

    DiscountSetting getDiscountSetting();
}

package com.assessments.luckyshop.infrastructure.util;

import java.math.BigDecimal;

public class ShopUtils {
    public static BigDecimal calculateTotalAmount(BigDecimal price, long quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}

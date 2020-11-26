package com.assessments.luckyshop.infrastructure.util;

import com.assessments.luckyshop.product.model.entity.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class ShopUtils {
    public static BigDecimal calculateTotalAmount(Map<Long, Product> products) {
        return products.entrySet().stream()
                .map(quantityProduct -> calculateAmount(quantityProduct.getValue().getPrice(), quantityProduct.getKey()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal calculateAmount(BigDecimal price, long quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public static BigDecimal calculateDiscountAmount(BigDecimal totalAmount, int percentage) {
        return totalAmount
                .multiply(BigDecimal.valueOf(percentage))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
    }
}

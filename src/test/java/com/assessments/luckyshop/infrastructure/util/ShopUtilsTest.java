package com.assessments.luckyshop.infrastructure.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ShopUtilsTest {
    @Test
    void calculateTotalAmount_withValidParameters_shouldPass() {
        //given
        BigDecimal price = BigDecimal.TEN;
        long quantity = 1L;

        //when
        BigDecimal calculatedAmount = ShopUtils.calculateTotalAmount(price, quantity);

        //then
        assertThat(calculatedAmount)
                .isPositive()
                .isEqualTo(BigDecimal.TEN);
    }
}
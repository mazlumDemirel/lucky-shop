package com.assessments.luckyshop.discount.command;

import com.assessments.luckyshop.discount.model.enums.DiscountSetting;
import com.assessments.luckyshop.infrastructure.util.ShopUtils;
import com.assessments.luckyshop.infrastructure.util.TransactionIdGenerator;
import com.assessments.luckyshop.product.model.entity.Product;
import com.assessments.luckyshop.product.model.enums.ProductType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AmountDiscountCommandTest {
    @Test
    void execute_withValidParametersAppendsDiscountAmountToTotalDiscountAmount_shouldPass() {
        //given
        String productId1 = TransactionIdGenerator.generate();
        String productId2 = TransactionIdGenerator.generate();

        Map<Long, Product> products = Map.of(
                10L, new Product() {{
                    setTransactionId(productId1);
                    setPrice(BigDecimal.TEN);
                    setProductType(ProductType.OTHERS);
                }},
                150L, new Product() {{
                    setTransactionId(productId2);
                    setPrice(BigDecimal.ONE);
                    setProductType(ProductType.GROCERIES);
                }}
        );

        DiscountCommand discountCommand = new AmountDiscountCommand(products);
        BigDecimal totalDiscountAmount = BigDecimal.TEN;

        //then
        assertThat(discountCommand.isApplicable()).isTrue();

        BigDecimal quotient = ShopUtils.calculateAmount(products.get(10L).getPrice(), 10L)
                .divide(BigDecimal.valueOf(100), RoundingMode.UP)
                .setScale(0, RoundingMode.DOWN);

        assertThat(discountCommand.execute(totalDiscountAmount))
                .isEqualTo(quotient
                        .multiply(BigDecimal.valueOf(DiscountSetting.AMOUNT.getDiscountAmount()))
                );

    }

    @Test
    void execute_withNotEligibleProductsIsNotApplicable_shouldPass() {
        //given
        String productId1 = TransactionIdGenerator.generate();
        String productId2 = TransactionIdGenerator.generate();

        Map<Long, Product> products = Map.of(
                99L, new Product() {{
                    setTransactionId(productId1);
                    setPrice(BigDecimal.ONE);
                    setProductType(ProductType.OTHERS);
                }},
                15L, new Product() {{
                    setTransactionId(productId2);
                    setPrice(BigDecimal.TEN);
                    setProductType(ProductType.GROCERIES);
                }}
        );

        DiscountCommand discountCommand = new AmountDiscountCommand(products);

        //then
        assertThat(discountCommand.isApplicable()).isFalse();
    }
}
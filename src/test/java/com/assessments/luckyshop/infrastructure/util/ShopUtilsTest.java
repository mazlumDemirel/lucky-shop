package com.assessments.luckyshop.infrastructure.util;

import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
import com.assessments.luckyshop.api.dto.request.ProductCount;
import com.assessments.luckyshop.product.model.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ShopUtilsTest {
    @Test
    void calculateAmount_withValidParameters_shouldPass() {
        //given
        BigDecimal price = BigDecimal.TEN;
        long quantity = 1L;

        //when
        BigDecimal calculatedAmount = ShopUtils.calculateAmount(price, quantity);

        //then
        assertThat(calculatedAmount)
                .isPositive()
                .isEqualTo(BigDecimal.TEN);
    }

    @Test
    void calculateTotalAmount_withValidParameters_shouldPass() {
        //given
        Map<Long, Product> products = Map.of(
                10L, new Product() {{
                    setPrice(BigDecimal.ONE);
                }},
                1L, new Product() {{
                    setPrice(BigDecimal.TEN);
                }}
        );

        //when
        BigDecimal totalAmount = ShopUtils.calculateTotalAmount(products);

        //then
        assertThat(totalAmount).isEqualTo(BigDecimal.valueOf(20));
    }

    @Test
    void groupProductsByQuantities_withValidParameters_shouldPass() {
        //given
        String productId1 = TransactionIdGenerator.generate();
        String productId2 = TransactionIdGenerator.generate();

        List<ProductCount> productCounts = List.of(
                ProductCount.builder().productId(productId1).quantity(10L).build(),
                ProductCount.builder().productId(productId2).quantity(1L).build()
        );

        CreateBillRequest createBillRequest = CreateBillRequest.builder().build();
        createBillRequest.setProducts(productCounts);

        List<Product> products = List.of(
                new Product() {{
                    setTransactionId(productId1);
                }},
                new Product() {{
                    setTransactionId(productId2);
                }}
        );

        //when
        Map<Long, Product> groupedProducts = ShopUtils.groupProductsByQuantities(createBillRequest, products);

        //then
        assertThat(groupedProducts)
                .isNotEmpty()
                .containsEntry(10L, products.get(0))
                .containsEntry(1L, products.get(1));
    }
}
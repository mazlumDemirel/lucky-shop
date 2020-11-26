package com.assessments.luckyshop.bill.service;

import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
import com.assessments.luckyshop.api.dto.request.ProductCount;
import com.assessments.luckyshop.api.dto.response.BillResponse;
import com.assessments.luckyshop.bill.service.impl.BillServiceImpl;
import com.assessments.luckyshop.discount.service.DiscountService;
import com.assessments.luckyshop.infrastructure.model.ApplicationErrorCode;
import com.assessments.luckyshop.infrastructure.model.exception.ShopException;
import com.assessments.luckyshop.infrastructure.util.ShopUtils;
import com.assessments.luckyshop.product.model.entity.Product;
import com.assessments.luckyshop.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class BillServiceTest {

    private BillService billService;

    @Mock
    private ProductService productService;
    @Mock
    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        billService = new BillServiceImpl(productService, discountService);
    }

    @Test
    void createBill_withValidRequest_shouldPass() {
        //given
        String productId = "dummy-product-id";
        String userId = "dummy-user-id";
        long quantity = 1L;

        BigDecimal productPrice = BigDecimal.valueOf(100L);

        List<Product> expectedProducts = List.of(new Product() {{
            setPrice(productPrice);
            setTransactionId(productId);
        }});

        List<String> productIds = List.of(productId);

        BigDecimal calculatedDiscountAmount = BigDecimal.valueOf(10);

        List<ProductCount> productCounts = List.of(ProductCount.builder().productId(productId).quantity(1L).build());

        CreateBillRequest createBillRequest = CreateBillRequest.builder()
                .products(productCounts)
                .userId(userId)
                .build();

        given(productService.getProducts(argThat(argument -> argument.containsAll(productIds)))).willReturn(expectedProducts);
        given(discountService.calculateDiscount(eq(createBillRequest), argThat(argument -> argument.containsKey(quantity) && argument.containsValue(expectedProducts.get(0))))).willReturn(calculatedDiscountAmount);

        //when
        BillResponse billResponse = billService.createBill(createBillRequest);

        //then
        verify(productService).getProducts(productIds);
        verify(discountService).calculateDiscount(eq(createBillRequest), argThat(argument -> argument.containsKey(quantity) && argument.containsValue(expectedProducts.get(0))));
        assertThat(billResponse)
                .isNotNull()
                .hasFieldOrPropertyWithValue("amount", ShopUtils.calculateAmount(productPrice, quantity))
                .hasFieldOrPropertyWithValue("discountAmount", calculatedDiscountAmount);
    }

    @Test
    void createBill_withInValidProductId_shouldFail() {
        //given
        String productId = "dummy-product-id";
        String userId = "dummy-user-id";

        List<String> productIds = List.of(productId);

        List<ProductCount> productCounts = List.of(ProductCount.builder().productId(productId).quantity(1L).build());

        CreateBillRequest createBillRequest = CreateBillRequest.builder()
                .products(productCounts)
                .userId(userId)
                .build();

        given(productService.getProducts(argThat(argument -> argument.containsAll(productIds)))).willThrow(new ShopException(ApplicationErrorCode.NOT_FOUND));

        //then
        assertThatThrownBy(() -> billService.createBill(createBillRequest))
                .isInstanceOf(ShopException.class)
                .hasFieldOrPropertyWithValue("httpStatus", HttpStatus.NOT_FOUND);

        verify(productService).getProducts(productIds);
        verifyNoInteractions(discountService);
    }
}
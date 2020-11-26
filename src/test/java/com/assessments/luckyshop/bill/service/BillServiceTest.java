package com.assessments.luckyshop.bill.service;

import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

        Product expectedProduct = new Product();
        expectedProduct.setPrice(productPrice);

        BigDecimal calculatedDiscountAmount = BigDecimal.valueOf(10);

        CreateBillRequest createBillRequest = CreateBillRequest.builder()
                .quantity(quantity)
                .userId(userId)
                .build();

        given(productService.getProduct(productId)).willReturn(expectedProduct);
        given(discountService.calculateDiscount(createBillRequest, expectedProduct)).willReturn(calculatedDiscountAmount);

        //when
        BillResponse billResponse = billService.createBill(productId, createBillRequest);

        //then
        verify(productService).getProduct(productId);
        verify(discountService).calculateDiscount(createBillRequest, expectedProduct);
        assertThat(billResponse)
                .isNotNull()
                .hasFieldOrPropertyWithValue("amount", ShopUtils.calculateTotalAmount(productPrice, quantity))
                .hasFieldOrPropertyWithValue("discountAmount", calculatedDiscountAmount);
    }

    @Test
    void createBill_withInValidProductId_shouldFail() {
        //given
        String productId = "dummy-product-id";
        String userId = "dummy-user-id";
        long quantity = 1L;

        CreateBillRequest createBillRequest = CreateBillRequest.builder()
                .quantity(quantity)
                .userId(userId)
                .build();

        given(productService.getProduct(productId)).willThrow(new ShopException(ApplicationErrorCode.NOT_FOUND));

        //then
        assertThatThrownBy(() -> billService.createBill(productId, createBillRequest))
                .isInstanceOf(ShopException.class)
                .hasFieldOrPropertyWithValue("httpStatus", HttpStatus.NOT_FOUND);

        verify(productService).getProduct(productId);
        verifyNoInteractions(discountService);
    }
}
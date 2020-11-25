package com.assessments.luckyshop.bill.impl;

import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
import com.assessments.luckyshop.api.dto.response.BillResponse;
import com.assessments.luckyshop.bill.BillService;
import com.assessments.luckyshop.discount.DiscountService;
import com.assessments.luckyshop.infrastructure.util.ShopUtils;
import com.assessments.luckyshop.product.ProductService;
import com.assessments.luckyshop.product.model.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final ProductService productService;
    private final DiscountService discountService;

    @Override
    public BillResponse createBill(String productId, CreateBillRequest billRequest) {
        Product product = productService.getProduct(productId);

        BigDecimal discountAmount = discountService.calculateDiscount(billRequest, product);

        return BillResponse.builder()
                .amount(ShopUtils.calculateTotalAmount(product.getPrice(), billRequest.getQuantity()))
                .discountAmount(discountAmount)
                .build();
    }
}

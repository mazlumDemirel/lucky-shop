package com.assessments.luckyshop.discount.service;

import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
import com.assessments.luckyshop.product.model.entity.Product;

import java.math.BigDecimal;

public interface DiscountService {
    BigDecimal calculateDiscount(CreateBillRequest createBillRequest, Product product);
}

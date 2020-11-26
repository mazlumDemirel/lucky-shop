package com.assessments.luckyshop.discount.service;

import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
import com.assessments.luckyshop.product.model.entity.Product;

import java.math.BigDecimal;
import java.util.Map;

public interface DiscountService {
    BigDecimal calculateDiscount(CreateBillRequest createBillRequest, Map<Long, Product> productsByQuantities);
}

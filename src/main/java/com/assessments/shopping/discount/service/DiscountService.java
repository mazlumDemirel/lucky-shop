package com.assessments.shopping.discount.service;

import com.assessments.shopping.api.dto.request.CreateBillRequest;
import com.assessments.shopping.product.model.entity.Product;

import java.math.BigDecimal;
import java.util.Map;

public interface DiscountService {
    BigDecimal calculateDiscount(CreateBillRequest createBillRequest, Map<Long, Product> productsByQuantities);
}

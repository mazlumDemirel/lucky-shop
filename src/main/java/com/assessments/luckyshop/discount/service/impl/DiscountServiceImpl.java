package com.assessments.luckyshop.discount.service.impl;

import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
import com.assessments.luckyshop.discount.service.DiscountService;
import com.assessments.luckyshop.product.model.entity.Product;
import com.assessments.luckyshop.user.model.entity.User;
import com.assessments.luckyshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final UserService userService;

    @Override
    public BigDecimal calculateDiscount(CreateBillRequest createBillRequest, List<Product> products) {
        User user = userService.getUser(createBillRequest.getUserId());

        return null;
    }
}

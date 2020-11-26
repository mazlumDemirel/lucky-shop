package com.assessments.luckyshop.bill.service.impl;

import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
import com.assessments.luckyshop.api.dto.request.ProductCount;
import com.assessments.luckyshop.api.dto.response.BillResponse;
import com.assessments.luckyshop.bill.service.BillService;
import com.assessments.luckyshop.discount.service.DiscountService;
import com.assessments.luckyshop.infrastructure.util.ShopUtils;
import com.assessments.luckyshop.product.model.entity.Product;
import com.assessments.luckyshop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.assessments.luckyshop.infrastructure.util.ShopUtils.groupProductsByQuantities;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final ProductService productService;
    private final DiscountService discountService;

    @Override
    public BillResponse createBill(CreateBillRequest billRequest) {
        List<String> productIds = billRequest.getProducts().stream().map(ProductCount::getProductId).collect(Collectors.toList());
        List<Product> products = productService.getProducts(productIds);

        Map<Long, Product> productsByQuantities = groupProductsByQuantities(billRequest, products);

        BigDecimal discountAmount = discountService.calculateDiscount(billRequest, productsByQuantities);

        return BillResponse.builder()
                .amount(ShopUtils.calculateTotalAmount(productsByQuantities))
                .discountAmount(discountAmount)
                .build();
    }
}

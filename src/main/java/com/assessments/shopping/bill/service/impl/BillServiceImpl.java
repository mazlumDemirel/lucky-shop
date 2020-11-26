package com.assessments.shopping.bill.service.impl;

import com.assessments.shopping.api.dto.request.CreateBillRequest;
import com.assessments.shopping.api.dto.request.ProductCount;
import com.assessments.shopping.api.dto.response.BillResponse;
import com.assessments.shopping.bill.service.BillService;
import com.assessments.shopping.discount.service.DiscountService;
import com.assessments.shopping.infrastructure.util.ShopUtils;
import com.assessments.shopping.product.model.entity.Product;
import com.assessments.shopping.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.assessments.shopping.infrastructure.util.ShopUtils.groupProductsByQuantities;

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

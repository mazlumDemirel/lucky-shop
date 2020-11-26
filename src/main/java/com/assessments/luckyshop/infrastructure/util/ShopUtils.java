package com.assessments.luckyshop.infrastructure.util;

import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
import com.assessments.luckyshop.api.dto.request.ProductCount;
import com.assessments.luckyshop.product.model.entity.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShopUtils {
    public static BigDecimal calculateTotalAmount(Map<Long, Product> products) {
        return products.entrySet().stream()
                .map(quantityProduct -> calculateAmount(quantityProduct.getValue().getPrice(), quantityProduct.getKey()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal calculateAmount(BigDecimal price, long quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public static BigDecimal calculateDiscountAmount(BigDecimal totalAmount, int percentage) {
        return totalAmount
                .multiply(BigDecimal.valueOf(percentage))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
    }

    public static Map<Long, Product> groupProductsByQuantities(CreateBillRequest billRequest, List<Product> products) {
        Map<String, Long> productQuantities = billRequest.getProducts().stream().collect(Collectors.toMap(ProductCount::getProductId, ProductCount::getQuantity));
        Map<String, Product> productsByIds = products.stream().collect(Collectors.toMap(Product::getTransactionId, product -> product));
        return productQuantities
                .entrySet()
                .stream()
                .map(productIdQuantityEntry ->
                        Map.entry(productIdQuantityEntry.getValue(), productsByIds.get(productIdQuantityEntry.getKey()))
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

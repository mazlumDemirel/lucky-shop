package com.assessments.luckyshop.product.service.impl;

import com.assessments.luckyshop.infrastructure.model.ApplicationErrorCode;
import com.assessments.luckyshop.infrastructure.model.exception.ShopException;
import com.assessments.luckyshop.product.model.entity.Product;
import com.assessments.luckyshop.product.repository.ProductRepository;
import com.assessments.luckyshop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product getProduct(String productId) {
        return productRepository
                .findByTransactionId(productId)
                .orElseThrow(
                        () -> {
                            throw new ShopException(ApplicationErrorCode.NOT_FOUND);
                        });
    }
}

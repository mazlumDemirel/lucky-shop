package com.assessments.luckyshop.product.service.impl;

import com.assessments.luckyshop.infrastructure.model.ApplicationErrorCode;
import com.assessments.luckyshop.infrastructure.model.exception.ShopException;
import com.assessments.luckyshop.product.model.entity.Product;
import com.assessments.luckyshop.product.repository.ProductRepository;
import com.assessments.luckyshop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getProducts(List<String> productIds) {
        List<Product> products = productRepository.findByTransactionIdIn(productIds);
        if (products.size() != productIds.size())
            throw new ShopException(ApplicationErrorCode.NOT_FOUND);
        return products;
    }
}

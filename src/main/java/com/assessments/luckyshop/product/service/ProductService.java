package com.assessments.luckyshop.product.service;

import com.assessments.luckyshop.product.model.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(List<String> productIds);
}

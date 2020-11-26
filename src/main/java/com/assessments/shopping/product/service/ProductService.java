package com.assessments.shopping.product.service;

import com.assessments.shopping.product.model.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(List<String> productIds);
}

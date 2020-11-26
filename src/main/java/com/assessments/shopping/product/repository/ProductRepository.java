package com.assessments.shopping.product.repository;

import com.assessments.shopping.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTransactionIdIn(List<String> transactionIds);
}

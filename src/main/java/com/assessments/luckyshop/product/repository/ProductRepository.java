package com.assessments.luckyshop.product.repository;

import com.assessments.luckyshop.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTransactionIdIn(List<String> transactionIds);
}

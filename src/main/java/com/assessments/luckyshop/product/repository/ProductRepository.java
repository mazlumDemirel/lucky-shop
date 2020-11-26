package com.assessments.luckyshop.product.repository;

import com.assessments.luckyshop.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByTransactionId(String transactionId);
}

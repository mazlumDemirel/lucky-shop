package com.assessments.luckyshop.product.repository;

import com.assessments.luckyshop.infrastructure.util.TransactionIdGenerator;
import com.assessments.luckyshop.product.model.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Rollback
class ProductRepositoryIntegrationTest {

    private final String savedProductTransactionId = TransactionIdGenerator.generate();
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Product product = new Product();
        product.setTransactionId(savedProductTransactionId);
        productRepository.save(product);
    }

    @Test
    void findByTransactionId_withValidProductId_shouldPass() {
        //when
        Optional<Product> productWrapper = productRepository.findByTransactionId(savedProductTransactionId);

        //then
        assertThat(productWrapper)
                .isPresent();
    }

    @Test
    void findByTransactionId_withInValidProductId_shouldFail() {
        //given
        String dummyProductId = "dummy-product-id";

        //when
        Optional<Product> productWrapper = productRepository.findByTransactionId(dummyProductId);

        //then
        assertThat(productWrapper).isNotPresent();
    }
}
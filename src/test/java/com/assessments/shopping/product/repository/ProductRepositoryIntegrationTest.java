package com.assessments.shopping.product.repository;

import com.assessments.shopping.infrastructure.util.TransactionIdGenerator;
import com.assessments.shopping.product.model.entity.Product;
import com.assessments.shopping.product.model.enums.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.List;

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
        product.setProductType(ProductType.OTHERS);
        product.setPrice(BigDecimal.ONE);
        product.setTransactionId(savedProductTransactionId);
        productRepository.save(product);
    }

    @Test
    void findByTransactionIdIn_withValidProductId_shouldPass() {
        //when
        List<Product> products = productRepository.findByTransactionIdIn(List.of(savedProductTransactionId));

        //then
        assertThat(products)
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void findByTransactionIdIn_withInValidProductId_shouldFail() {
        //given
        String dummyProductId = "dummy-product-id";

        //when
        List<Product> products = productRepository.findByTransactionIdIn(List.of(dummyProductId));

        //then
        assertThat(products).isEmpty();
    }
}
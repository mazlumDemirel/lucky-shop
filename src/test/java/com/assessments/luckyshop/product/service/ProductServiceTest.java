package com.assessments.luckyshop.product.service;

import com.assessments.luckyshop.infrastructure.model.exception.ShopException;
import com.assessments.luckyshop.product.model.entity.Product;
import com.assessments.luckyshop.product.repository.ProductRepository;
import com.assessments.luckyshop.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void getProduct_withValidProductId_shouldPass() {
        //given
        String productId = "dummy-product-id";
        Product expectedProduct = new Product();
        given(productRepository.findByTransactionId(productId)).willReturn(Optional.of(expectedProduct));

        //when
        Product product = productService.getProduct(productId);

        //then
        verify(productRepository).findByTransactionId(productId);
        assertThat(product)
                .isNotNull()
                .isEqualTo(product);
    }

    @Test
    void getProduct_withInValidProductId_shouldFail() {
        //given
        String productId = "dummy-product-id";
        given(productRepository.findByTransactionId(productId)).willReturn(Optional.empty());

        //then
        verifyNoInteractions(productRepository);
        assertThatThrownBy(() -> productService.getProduct(productId))
                .isInstanceOf(ShopException.class)
                .hasFieldOrPropertyWithValue("httpStatus", HttpStatus.NOT_FOUND);
    }
}
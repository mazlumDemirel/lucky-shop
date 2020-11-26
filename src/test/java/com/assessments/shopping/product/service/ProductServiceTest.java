package com.assessments.shopping.product.service;

import com.assessments.shopping.infrastructure.model.exception.ShopException;
import com.assessments.shopping.product.model.entity.Product;
import com.assessments.shopping.product.repository.ProductRepository;
import com.assessments.shopping.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

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
    void getProducts_withValidProductIds_shouldPass() {
        //given
        String productId = "dummy-product-id";
        Product expectedProduct = new Product();
        List<String> productIds = List.of(productId);
        given(productRepository.findByTransactionIdIn(productIds)).willReturn(List.of(expectedProduct));

        //when
        List<Product> products = productService.getProducts(productIds);

        //then
        verify(productRepository).findByTransactionIdIn(productIds);
        assertThat(products)
                .isNotNull()
                .isEqualTo(products);
    }

    @Test
    void getProducts_withInValidProductIds_shouldFail() {
        //given
        String productId = "dummy-product-id";
        List<String> productIds = List.of(productId);
        given(productRepository.findByTransactionIdIn(productIds)).willReturn(List.of());

        //then
        verifyNoInteractions(productRepository);
        assertThatThrownBy(() -> productService.getProducts(productIds))
                .isInstanceOf(ShopException.class)
                .hasFieldOrPropertyWithValue("httpStatus", HttpStatus.NOT_FOUND);
    }
}
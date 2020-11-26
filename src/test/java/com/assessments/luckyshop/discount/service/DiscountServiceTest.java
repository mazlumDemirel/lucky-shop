package com.assessments.luckyshop.discount.service;

import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
import com.assessments.luckyshop.api.dto.request.ProductCount;
import com.assessments.luckyshop.discount.model.enums.DiscountSetting;
import com.assessments.luckyshop.discount.service.impl.DiscountServiceImpl;
import com.assessments.luckyshop.infrastructure.model.exception.ShopException;
import com.assessments.luckyshop.infrastructure.util.ShopUtils;
import com.assessments.luckyshop.infrastructure.util.TransactionIdGenerator;
import com.assessments.luckyshop.product.model.entity.Product;
import com.assessments.luckyshop.product.model.enums.ProductType;
import com.assessments.luckyshop.user.model.entity.User;
import com.assessments.luckyshop.user.model.enums.UserType;
import com.assessments.luckyshop.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static com.assessments.luckyshop.infrastructure.model.ApplicationErrorCode.NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {
    private final Clock fixedClockNow = Clock.fixed(Instant.parse("2020-11-26T00:00:01Z"), ZoneId.of("UTC"));
    private DiscountService discountService;
    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        discountService = new DiscountServiceImpl(userService, fixedClockNow);
    }

    @Test
    void calculateDiscount_withValidParameters_shouldPass() {
        //given
        String productId1 = TransactionIdGenerator.generate();
        String productId2 = TransactionIdGenerator.generate();
        String userId = TransactionIdGenerator.generate();

        Map<Long, Product> products = Map.of(
                10L, new Product() {{
                    setTransactionId(productId1);
                    setPrice(BigDecimal.ONE);
                    setProductType(ProductType.OTHERS);
                }},
                1L, new Product() {{
                    setTransactionId(productId2);
                    setPrice(BigDecimal.TEN);
                    setProductType(ProductType.GROCERIES);
                }}
        );

        List<ProductCount> productCounts = List.of(
                ProductCount.builder().productId(productId1).quantity(10L).build(),
                ProductCount.builder().productId(productId2).quantity(1L).build()
        );

        CreateBillRequest createBillRequest = CreateBillRequest
                .builder()
                .products(productCounts)
                .userId(userId)
                .build();

        given(userService.getUser(userId)).willReturn(new User() {{
            setUserType(UserType.AFFILIATE);
        }});

        //when
        BigDecimal discountAmount = discountService.calculateDiscount(createBillRequest, products);

        //then
        verify(userService).getUser(userId);
        assertThat(discountAmount)
                .isEqualTo(
                        ShopUtils.calculateDiscountAmount(BigDecimal.
                                valueOf(productCounts.get(0).getQuantity())
                                .multiply(products.get(10L).getPrice())
                                .add(BigDecimal.
                                        valueOf(productCounts.get(1).getQuantity())
                                        .multiply(products.get(1L).getPrice())), DiscountSetting.AFFILIATE.getDiscountAmount())
                );
    }

    @Test
    void calculateDiscount_withInValidUserId_shouldFail() {
        //given
        String productId1 = TransactionIdGenerator.generate();
        String productId2 = TransactionIdGenerator.generate();
        String userId = TransactionIdGenerator.generate();

        Map<Long, Product> products = Map.of(
                10L, new Product() {{
                    setTransactionId(productId1);
                    setPrice(BigDecimal.ONE);
                    setProductType(ProductType.OTHERS);
                }},
                1L, new Product() {{
                    setTransactionId(productId2);
                    setPrice(BigDecimal.TEN);
                    setProductType(ProductType.GROCERIES);
                }}
        );

        List<ProductCount> productCounts = List.of(
                ProductCount.builder().productId(productId1).quantity(10L).build(),
                ProductCount.builder().productId(productId2).quantity(1L).build()
        );

        CreateBillRequest createBillRequest = CreateBillRequest
                .builder()
                .products(productCounts)
                .userId(userId)
                .build();

        given(userService.getUser(userId)).willThrow(new ShopException(NOT_FOUND));

        //then
        assertThatThrownBy(() -> discountService.calculateDiscount(createBillRequest, products))
                .isInstanceOf(ShopException.class)
                .hasMessage(NOT_FOUND.getMessage());
        verify(userService).getUser(userId);
    }
}
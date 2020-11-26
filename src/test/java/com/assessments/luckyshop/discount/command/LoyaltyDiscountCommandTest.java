package com.assessments.luckyshop.discount.command;

import com.assessments.luckyshop.discount.model.enums.DiscountSetting;
import com.assessments.luckyshop.infrastructure.util.ShopUtils;
import com.assessments.luckyshop.infrastructure.util.TransactionIdGenerator;
import com.assessments.luckyshop.product.model.entity.Product;
import com.assessments.luckyshop.product.model.enums.ProductType;
import com.assessments.luckyshop.user.model.entity.User;
import com.assessments.luckyshop.user.model.enums.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class LoyaltyDiscountCommandTest {

    private final Clock fixedClockNow = Clock.fixed(Instant.parse("2020-11-26T00:00:01Z"), ZoneId.of("UTC"));

    @Test
    void execute_withValidParametersAppendsDiscountAmountToTotalDiscountAmount_shouldPass() {
        //given
        String productId1 = TransactionIdGenerator.generate();
        String productId2 = TransactionIdGenerator.generate();

        Map<Long, Product> products = Map.of(
                10L, new Product() {{
                    setTransactionId(productId1);
                    setPrice(BigDecimal.TEN);
                    setProductType(ProductType.OTHERS);
                }},
                100L, new Product() {{
                    setTransactionId(productId2);
                    setPrice(BigDecimal.ONE);
                    setProductType(ProductType.GROCERIES);
                }}
        );

        User user = new User() {{
            setUserType(UserType.MEMBER);
            setCreatedAt(LocalDateTime.parse("2018-11-25T00:00:01Z", DateTimeFormatter.ISO_DATE_TIME));
        }};

        DiscountCommand discountCommand = new LoyaltyDiscountCommand(user, products, fixedClockNow);
        BigDecimal totalDiscountAmount = BigDecimal.TEN;

        //then
        assertThat(discountCommand.isApplicable()).isTrue();

        assertThat(discountCommand.execute(totalDiscountAmount))
                .isEqualTo(
                        ShopUtils.calculateDiscountAmount(ShopUtils.calculateTotalAmount(products).subtract(totalDiscountAmount), DiscountSetting.LOYALTY.getDiscountAmount())
                );

    }

    @Test
    void execute_withNotEligibleUserIsNotApplicable_shouldPass() {
        //given
        String productId1 = TransactionIdGenerator.generate();
        String productId2 = TransactionIdGenerator.generate();

        Map<Long, Product> products = Map.of(
                10L, new Product() {{
                    setTransactionId(productId1);
                    setPrice(BigDecimal.TEN);
                    setProductType(ProductType.OTHERS);
                }},
                100L, new Product() {{
                    setTransactionId(productId2);
                    setPrice(BigDecimal.ONE);
                    setProductType(ProductType.GROCERIES);
                }}
        );

        User user = new User() {{
            setUserType(UserType.MEMBER);
            setCreatedAt(LocalDateTime.parse("2018-11-27T00:00:01Z", DateTimeFormatter.ISO_DATE_TIME));
        }};

        DiscountCommand discountCommand = new LoyaltyDiscountCommand(user, products, fixedClockNow);

        //then
        assertThat(discountCommand.isApplicable()).isFalse();
    }
}
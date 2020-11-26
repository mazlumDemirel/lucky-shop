package com.assessments.shopping.discount.command;

import com.assessments.shopping.discount.model.enums.DiscountSetting;
import com.assessments.shopping.infrastructure.util.ShopUtils;
import com.assessments.shopping.infrastructure.util.TransactionIdGenerator;
import com.assessments.shopping.product.model.entity.Product;
import com.assessments.shopping.product.model.enums.ProductType;
import com.assessments.shopping.user.model.entity.User;
import com.assessments.shopping.user.model.enums.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AffiliateDiscountCommandTest {
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
            setUserType(UserType.AFFILIATE);
        }};

        DiscountCommand discountCommand = new AffiliateDiscountCommand(user, products);
        BigDecimal totalDiscountAmount = BigDecimal.TEN;

        //then
        assertThat(discountCommand.isApplicable()).isTrue();

        assertThat(discountCommand.execute(totalDiscountAmount))
                .isEqualTo(
                        ShopUtils.calculateDiscountAmount(ShopUtils.calculateTotalAmount(products).subtract(totalDiscountAmount), DiscountSetting.AFFILIATE.getDiscountAmount())
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
        }};

        DiscountCommand discountCommand = new AffiliateDiscountCommand(user, products);

        //then
        assertThat(discountCommand.isApplicable()).isFalse();
    }
}
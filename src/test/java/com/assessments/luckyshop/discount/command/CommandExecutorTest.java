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
import java.math.RoundingMode;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CommandExecutorTest {

    @Test
    void executeCommands_withAddPercentageCommandAndExecuteItWithValidParameters_shouldPass() {
        //given
        CommandExecutor commandExecutor = new CommandExecutor();
        String userId = TransactionIdGenerator.generate();

        User user = new User() {{
            setTransactionId(userId);
            setUserType(UserType.AFFILIATE);
        }};

        String productId1 = TransactionIdGenerator.generate();
        String productId2 = TransactionIdGenerator.generate();

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

        DiscountCommand discountCommand = new AffiliateDiscountCommand(user, products);
        commandExecutor.addCommand(discountCommand);

        //when
        BigDecimal discountAmount = commandExecutor.executeCommands();

        //then
        assertThat(discountAmount)
                .isEqualTo(
                        ShopUtils.calculateDiscountAmount(BigDecimal.
                                valueOf(10L)
                                .multiply(products.get(10L).getPrice())
                                .add(BigDecimal.
                                        valueOf(1L)
                                        .multiply(products.get(1L).getPrice())), DiscountSetting.AFFILIATE.getDiscountAmount())
                );
    }

    @Test
    void executeCommands_withAddAmountCommandAndExecuteItWithValidParameters_shouldPass() {
        //given
        CommandExecutor commandExecutor = new CommandExecutor();
        String userId = TransactionIdGenerator.generate();

        User user = new User() {{
            setTransactionId(userId);
            setUserType(UserType.AFFILIATE);
        }};

        String productId1 = TransactionIdGenerator.generate();
        String productId2 = TransactionIdGenerator.generate();

        Map<Long, Product> products = Map.of(
                10L, new Product() {{
                    setTransactionId(productId1);
                    setPrice(BigDecimal.TEN);
                    setProductType(ProductType.OTHERS);
                }},
                150L, new Product() {{
                    setTransactionId(productId2);
                    setPrice(BigDecimal.ONE);
                    setProductType(ProductType.GROCERIES);
                }}
        );

        DiscountCommand discountCommand = new AmountDiscountCommand(products);
        commandExecutor.addCommand(discountCommand);

        //when
        BigDecimal discountAmount = commandExecutor.executeCommands();

        //then
        BigDecimal quotient = ShopUtils.calculateAmount(products.get(10L).getPrice(), 10L)
                .divide(BigDecimal.valueOf(100), RoundingMode.UP)
                .setScale(0, RoundingMode.DOWN);

        assertThat(discountAmount)
                .isEqualTo(quotient.multiply(BigDecimal.valueOf(DiscountSetting.AMOUNT.getDiscountAmount())));
    }
}
package com.assessments.luckyshop.discount.command;

import com.assessments.luckyshop.discount.model.enums.DiscountSetting;
import com.assessments.luckyshop.infrastructure.util.ShopUtils;
import com.assessments.luckyshop.product.model.entity.Product;
import com.assessments.luckyshop.user.model.entity.User;
import com.assessments.luckyshop.user.model.enums.UserType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
public class LoyaltyDiscountCommand implements DiscountCommand {
    private static final DiscountSetting DISCOUNT_SETTING = DiscountSetting.LOYALTY;
    private final User user;
    private final Map<Long, Product> productsByQuantities;
    private final Clock clock;

    @Override
    public boolean isApplicable() {
        LocalDateTime twoYearsAgo = LocalDateTime.now(clock).minusYears(2);
        return user.getUserType().equals(UserType.MEMBER)
                && (user.getCreatedAt().isEqual(twoYearsAgo) || user.getCreatedAt().isBefore(twoYearsAgo));
    }

    @Override
    public BigDecimal execute(BigDecimal discountAmount) {
        BigDecimal totalAmount = ShopUtils.calculateTotalAmount(productsByQuantities).subtract(discountAmount);
        return ShopUtils.calculateDiscountAmount(totalAmount, DISCOUNT_SETTING.getDiscountAmount());
    }

    @Override
    public DiscountSetting getDiscountSetting() {
        return DISCOUNT_SETTING;
    }
}

package com.assessments.shopping.discount.command;

import com.assessments.shopping.discount.model.enums.DiscountSetting;
import com.assessments.shopping.infrastructure.util.ShopUtils;
import com.assessments.shopping.product.model.entity.Product;
import com.assessments.shopping.user.model.entity.User;
import com.assessments.shopping.user.model.enums.UserType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor
public class AffiliateDiscountCommand implements DiscountCommand {
    private static final DiscountSetting DISCOUNT_SETTING = DiscountSetting.AFFILIATE;
    private final User user;
    private final Map<Long, Product> productsByQuantities;

    @Override
    public boolean isApplicable() {
        return user.getUserType().equals(UserType.AFFILIATE);
    }

    @Override
    public BigDecimal execute(BigDecimal discountAmount) {
        BigDecimal totalAmount = ShopUtils.calculateTotalAmount(productsByQuantities).subtract(discountAmount);
        return ShopUtils.calculateDiscountAmount(totalAmount, DISCOUNT_SETTING.getDiscountAmount());
    }

    public DiscountSetting getDiscountSetting() {
        return DISCOUNT_SETTING;
    }
}

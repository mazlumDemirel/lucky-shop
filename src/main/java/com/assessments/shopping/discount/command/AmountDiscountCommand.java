package com.assessments.shopping.discount.command;

import com.assessments.shopping.discount.model.enums.DiscountSetting;
import com.assessments.shopping.infrastructure.util.ShopUtils;
import com.assessments.shopping.product.model.entity.Product;
import com.assessments.shopping.product.model.enums.ProductType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static java.util.List.of;

@RequiredArgsConstructor
public class AmountDiscountCommand implements DiscountCommand {
    private static final DiscountSetting DISCOUNT_SETTING = DiscountSetting.AMOUNT;
    private final Map<Long, Product> productsByQuantities;

    @Override
    public boolean isApplicable() {
        return productsByQuantities.entrySet().stream().anyMatch(longProductEntry ->
                longProductEntry.getValue().getProductType().equals(ProductType.OTHERS)
                        && of(0, 1).contains(ShopUtils.calculateAmount(longProductEntry.getValue().getPrice(), longProductEntry.getKey()).compareTo(BigDecimal.valueOf(100)))
        );
    }

    @Override
    public BigDecimal execute(BigDecimal discountAmount) {
        BigDecimal totalAmountOfEligibleProducts = productsByQuantities
                .entrySet()
                .stream()
                .filter(longProductEntry -> longProductEntry.getValue().getProductType().equals(ProductType.OTHERS))
                .map(longProductEntry ->
                        ShopUtils.calculateAmount(longProductEntry.getValue().getPrice(), longProductEntry.getKey())
                ).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal quotient = totalAmountOfEligibleProducts
                .divide(BigDecimal.valueOf(100), RoundingMode.UP)
                .setScale(0, RoundingMode.DOWN);

        return quotient
                .multiply(BigDecimal.valueOf(DISCOUNT_SETTING.getDiscountAmount()));
    }

    @Override
    public DiscountSetting getDiscountSetting() {
        return DISCOUNT_SETTING;
    }
}

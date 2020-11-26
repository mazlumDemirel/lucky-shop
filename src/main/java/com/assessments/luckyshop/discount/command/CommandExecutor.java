package com.assessments.luckyshop.discount.command;

import com.assessments.luckyshop.discount.model.enums.DiscountType;
import com.assessments.luckyshop.product.model.entity.Product;
import com.assessments.luckyshop.user.model.entity.User;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CommandExecutor {
    private final List<DiscountCommand> commands = new ArrayList<>();
    private final User user;
    private final Map<Long, Product> productsByQuantities;

    public BigDecimal executeCommands() {
        addCommand(new AmountDiscountCommand(productsByQuantities));
        addCommand(new AffiliateDiscountCommand(user, productsByQuantities));
        addCommand(new EmployeeDiscountCommand(user, productsByQuantities));
        addCommand(new LoyaltyDiscountCommand(user, productsByQuantities));

        BigDecimal discountAmount = BigDecimal.ZERO;

        List<DiscountCommand> percentageDiscounts = commands
                .stream()
                .filter(command -> command.getDiscountSetting().getDiscountType().equals(DiscountType.PERCENTAGE))
                .collect(Collectors.toList());

        for (DiscountCommand discountCommand : percentageDiscounts) {
            if (discountCommand.isApplicable()) {
                discountAmount = discountCommand.execute();
                break;
            }
        }

        commands.removeAll(percentageDiscounts);

        for (DiscountCommand command : commands) {
            if (command.isApplicable()) {
                discountAmount = discountAmount.add(command.execute());
            }
        }
        return discountAmount;
    }

    private void addCommand(DiscountCommand command) {
        commands.add(command);
    }
}

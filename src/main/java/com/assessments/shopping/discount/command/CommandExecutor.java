package com.assessments.shopping.discount.command;

import com.assessments.shopping.discount.model.enums.DiscountType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CommandExecutor {
    private final List<DiscountCommand> commands = new ArrayList<>();

    public BigDecimal executeCommands() {
        BigDecimal discountAmount = BigDecimal.ZERO;

        List<DiscountCommand> amountDiscounts = commands
                .stream()
                .filter(command -> command.getDiscountSetting().getDiscountType().equals(DiscountType.AMOUNT))
                .collect(Collectors.toList());

        for (DiscountCommand discountCommand : amountDiscounts) {
            if (discountCommand.isApplicable()) {
                discountAmount = discountCommand.execute(discountAmount);
                break;
            }
        }

        commands.removeAll(amountDiscounts);
        for (DiscountCommand command : commands) {
            if (command.isApplicable()) {
                discountAmount = discountAmount.add(command.execute(discountAmount));
            }
        }
        return discountAmount;
    }

    public void addCommand(DiscountCommand command) {
        commands.add(command);
    }
}

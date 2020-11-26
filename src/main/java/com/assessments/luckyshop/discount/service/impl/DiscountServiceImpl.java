package com.assessments.luckyshop.discount.service.impl;

import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
import com.assessments.luckyshop.discount.command.AffiliateDiscountCommand;
import com.assessments.luckyshop.discount.command.AmountDiscountCommand;
import com.assessments.luckyshop.discount.command.CommandExecutor;
import com.assessments.luckyshop.discount.command.EmployeeDiscountCommand;
import com.assessments.luckyshop.discount.command.LoyaltyDiscountCommand;
import com.assessments.luckyshop.discount.service.DiscountService;
import com.assessments.luckyshop.product.model.entity.Product;
import com.assessments.luckyshop.user.model.entity.User;
import com.assessments.luckyshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final UserService userService;
    private final Clock clock;

    @Override
    public BigDecimal calculateDiscount(CreateBillRequest createBillRequest, Map<Long, Product> productsByQuantities) {
        User user = userService.getUser(createBillRequest.getUserId());
        CommandExecutor commandExecutor = new CommandExecutor();

        commandExecutor.addCommand(new AmountDiscountCommand(productsByQuantities));
        commandExecutor.addCommand(new AffiliateDiscountCommand(user, productsByQuantities));
        commandExecutor.addCommand(new EmployeeDiscountCommand(user, productsByQuantities));
        commandExecutor.addCommand(new LoyaltyDiscountCommand(user, productsByQuantities, clock));

        return commandExecutor.executeCommands();
    }
}

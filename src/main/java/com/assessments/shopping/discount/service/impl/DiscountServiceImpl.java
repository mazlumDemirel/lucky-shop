package com.assessments.shopping.discount.service.impl;

import com.assessments.shopping.api.dto.request.CreateBillRequest;
import com.assessments.shopping.discount.command.AffiliateDiscountCommand;
import com.assessments.shopping.discount.command.AmountDiscountCommand;
import com.assessments.shopping.discount.command.CommandExecutor;
import com.assessments.shopping.discount.command.EmployeeDiscountCommand;
import com.assessments.shopping.discount.command.LoyaltyDiscountCommand;
import com.assessments.shopping.discount.service.DiscountService;
import com.assessments.shopping.product.model.entity.Product;
import com.assessments.shopping.user.model.entity.User;
import com.assessments.shopping.user.service.UserService;
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

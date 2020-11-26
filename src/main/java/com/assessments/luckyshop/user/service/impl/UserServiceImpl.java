package com.assessments.luckyshop.user.service.impl;

import com.assessments.luckyshop.infrastructure.model.ApplicationErrorCode;
import com.assessments.luckyshop.infrastructure.model.exception.ShopException;
import com.assessments.luckyshop.user.model.entity.User;
import com.assessments.luckyshop.user.repository.UserRepository;
import com.assessments.luckyshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser(String userId) {
        return userRepository
                .findByTransactionId(userId)
                .orElseThrow(() -> {
                    throw new ShopException(ApplicationErrorCode.NOT_FOUND);
                });
    }
}

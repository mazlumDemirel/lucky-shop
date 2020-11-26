package com.assessments.shopping.user.service.impl;

import com.assessments.shopping.infrastructure.model.ApplicationErrorCode;
import com.assessments.shopping.infrastructure.model.exception.ShopException;
import com.assessments.shopping.user.model.entity.User;
import com.assessments.shopping.user.repository.UserRepository;
import com.assessments.shopping.user.service.UserService;
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

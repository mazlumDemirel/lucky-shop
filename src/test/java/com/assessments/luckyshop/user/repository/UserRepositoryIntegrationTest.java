package com.assessments.luckyshop.user.repository;

import com.assessments.luckyshop.infrastructure.util.TransactionIdGenerator;
import com.assessments.luckyshop.user.model.entity.User;
import com.assessments.luckyshop.user.model.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Rollback
class UserRepositoryIntegrationTest {

    private final String savedUserTransactionId = TransactionIdGenerator.generate();
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUserType(UserType.MEMBER);
        user.setTransactionId(savedUserTransactionId);
        userRepository.save(user);
    }

    @Test
    void findByTransactionId_withValidUserId_shouldPass() {
        //when
        Optional<User> userWrapper = userRepository.findByTransactionId(savedUserTransactionId);

        //then
        assertThat(userWrapper)
                .isPresent();
    }

    @Test
    void findByTransactionId_withInValidUserId_shouldFail() {
        //given
        String dummyUserId = "dummy-user-id";

        //when
        Optional<User> userWrapper = userRepository.findByTransactionId(dummyUserId);

        //then
        assertThat(userWrapper).isNotPresent();
    }
}
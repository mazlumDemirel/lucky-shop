package com.assessments.luckyshop.user.service;

import com.assessments.luckyshop.infrastructure.model.exception.ShopException;
import com.assessments.luckyshop.user.model.entity.User;
import com.assessments.luckyshop.user.repository.UserRepository;
import com.assessments.luckyshop.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void getUser_withValidUserId_shouldPass() {
        //given
        String userId = "dummy-user-id";
        User expectedUser = new User();
        BDDMockito.given(userRepository.findByTransactionId(userId)).willReturn(Optional.of(expectedUser));

        //when
        User user = userService.getUser(userId);

        //then
        verify(userRepository).findByTransactionId(userId);
        assertThat(user)
                .isNotNull()
                .isEqualTo(user);
    }

    @Test
    void getUser_withInValidUserId_shouldFail() {
        //given
        String userId = "dummy-user-id";
        given(userRepository.findByTransactionId(userId)).willReturn(Optional.empty());

        //then
        verifyNoInteractions(userRepository);
        assertThatThrownBy(() -> userService.getUser(userId))
                .isInstanceOf(ShopException.class)
                .hasFieldOrPropertyWithValue("httpStatus", HttpStatus.NOT_FOUND);
    }
}
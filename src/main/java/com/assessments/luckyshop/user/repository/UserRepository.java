package com.assessments.luckyshop.user.repository;

import com.assessments.luckyshop.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByTransactionId(String transactionId);
}

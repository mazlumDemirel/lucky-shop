package com.assessments.shopping.user.repository;

import com.assessments.shopping.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByTransactionId(String transactionId);
}

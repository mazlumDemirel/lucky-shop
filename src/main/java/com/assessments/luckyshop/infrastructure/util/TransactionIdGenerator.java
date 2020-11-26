package com.assessments.luckyshop.infrastructure.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionIdGenerator {
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}

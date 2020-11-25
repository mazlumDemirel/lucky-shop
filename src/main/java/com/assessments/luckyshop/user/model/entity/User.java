package com.assessments.luckyshop.user.model.entity;

import com.assessments.luckyshop.user.model.enums.UserType;
import lombok.Data;

@Data
public class User {
    private UserType userType;
}

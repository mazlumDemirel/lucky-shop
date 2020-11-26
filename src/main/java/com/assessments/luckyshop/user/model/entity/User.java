package com.assessments.luckyshop.user.model.entity;

import com.assessments.luckyshop.infrastructure.entity.base.BaseEntity;
import com.assessments.luckyshop.user.model.enums.UserType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static com.assessments.luckyshop.infrastructure.constant.ShopConstants.DEFAULT_ID_GENERATOR_NAME;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
@SequenceGenerator(name = DEFAULT_ID_GENERATOR_NAME, sequenceName = "seq_user")
public class User extends BaseEntity {
    private UserType userType;
}

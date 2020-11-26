package com.assessments.shopping.user.model.entity;

import com.assessments.shopping.infrastructure.entity.base.BaseEntity;
import com.assessments.shopping.user.model.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "user")
@SequenceGenerator(name = "default_sequence_generator", sequenceName = "seq_user")
public class User extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserType userType;
}

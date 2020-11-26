package com.assessments.luckyshop.infrastructure.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @Setter
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "default_sequence_generator")
    private Long id;
    @Setter
    @Column(nullable = false)
    private String transactionId;
}
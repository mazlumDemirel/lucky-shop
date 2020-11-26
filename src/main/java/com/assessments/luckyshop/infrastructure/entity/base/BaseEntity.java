package com.assessments.luckyshop.infrastructure.entity.base;

import com.assessments.luckyshop.infrastructure.constant.ShopConstants;
import com.assessments.luckyshop.infrastructure.util.TransactionIdGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @Column(name = "created_at", updatable = false, nullable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = ShopConstants.DEFAULT_ID_GENERATOR_NAME)
    private Long id;
    @Setter
    private String transactionId;
    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        if (!StringUtils.hasText(transactionId))
            transactionId = TransactionIdGenerator.generate();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
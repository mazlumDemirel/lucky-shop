package com.assessments.shopping.product.model.entity;

import com.assessments.shopping.infrastructure.entity.base.BaseEntity;
import com.assessments.shopping.product.model.enums.ProductType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "product")
@SequenceGenerator(name = "default_sequence_generator", sequenceName = "seq_product")
public class Product extends BaseEntity {
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProductType productType;
}

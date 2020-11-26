package com.assessments.luckyshop.product.model.entity;

import com.assessments.luckyshop.infrastructure.entity.base.BaseEntity;
import com.assessments.luckyshop.product.model.enums.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

import static com.assessments.luckyshop.infrastructure.constant.ShopConstants.DEFAULT_ID_GENERATOR_NAME;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product")
@SequenceGenerator(name = DEFAULT_ID_GENERATOR_NAME, sequenceName = "seq_product")
public class Product extends BaseEntity {
    private BigDecimal price;
    private ProductType productType;
}

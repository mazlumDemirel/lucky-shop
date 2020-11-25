package com.assessments.luckyshop.product.model.entity;

import com.assessments.luckyshop.product.model.enums.ProductType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private String productName;
    private BigDecimal price;
    private ProductType productType;
}

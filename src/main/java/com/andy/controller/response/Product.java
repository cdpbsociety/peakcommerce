package com.andy.controller.response;

import lombok.Data;

import java.util.List;

@Data
public class Product {
    private String id;
    private List<ProductRatePlan> productRatePlans;
}

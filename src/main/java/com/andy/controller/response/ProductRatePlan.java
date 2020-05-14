package com.andy.controller.response;

import lombok.Data;

@Data
public class ProductRatePlan {
    private String id;
    private String status;
    private String name;

    public boolean isActive() {
        return status.equals("Active");
    }
}

package com.andy.controller.response;

import lombok.Data;

@Data
public class PaymentGateway {
    private String id;
    private String name;
    private Boolean isActive;

    public boolean hasNameAndIsActive(String name) {
        return this.name.equals(name) && isActive;
    }
}

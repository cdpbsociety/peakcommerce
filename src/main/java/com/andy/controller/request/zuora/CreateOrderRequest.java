package com.andy.controller.request.zuora;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private String accountNumber;
    private String ratePlanName;
}

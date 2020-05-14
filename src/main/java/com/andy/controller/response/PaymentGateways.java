package com.andy.controller.response;

import lombok.Data;

import java.util.List;

@Data
public class PaymentGateways {
    private List<PaymentGateway> paymentgateways;
}

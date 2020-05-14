package com.andy.service;

import com.andy.data.api.zuora.request.Payment;
import com.andy.data.api.zuora.request.PaymentMethod;
import com.andy.controller.response.PaymentGateway;
import com.andy.controller.response.PaymentGateways;
import com.andy.exception.NotFoundException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final ZuoraService zuoraService;

    @Autowired
    public PaymentService(ZuoraService zuoraService) {
        this.zuoraService = zuoraService;
    }

    public String create(Payment payment) {
        String response = this.zuoraService.postJson("/v1/payments", payment);
        return response;
    }

    public String createPaymentMethod(PaymentMethod paymentMethod ) {
        String response = this.zuoraService.postJson("/v1/payment-methods", paymentMethod);
        return response;
    }

    public PaymentGateway findPaymentGateway(String name) {
        String response = this.zuoraService.get("/v1/paymentgateways");
        PaymentGateways paymentGateways = new Gson().fromJson(response, PaymentGateways.class);
        if (paymentGateways.getPaymentgateways() != null) {
            for (PaymentGateway paymentGateway : paymentGateways.getPaymentgateways()) {
                if (paymentGateway.hasNameAndIsActive(name)) {
                    return paymentGateway;
                }
            }
        }
        throw new NotFoundException("Payment gateway not found for " + name);
    }
}

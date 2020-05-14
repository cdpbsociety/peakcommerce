package com.andy.service;

import com.andy.controller.response.ProductRatePlan;
import com.andy.data.api.zuora.request.SubscribeToRatePlan;
import com.andy.data.api.zuora.request.order.CreateOrder;
import com.andy.data.api.zuora.request.order.InitialTerm;
import com.andy.data.api.zuora.request.order.ProcessingOptions;
import com.andy.data.api.zuora.request.order.RenewalTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OrderService {
    private final ZuoraService zuoraService;

    @Autowired
    public OrderService(ZuoraService zuoraService) {
        this.zuoraService = zuoraService;
    }

    public String create(String customerAccountNumber, ProductRatePlan productRatePlan) {
        String today = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
        InitialTerm initialTerm = new InitialTerm(today, 12, "Month", "TERMED");
        RenewalTerm renewalTerm = new RenewalTerm(12, "Month");
        ProcessingOptions processingOptions = new ProcessingOptions(true, false);
        SubscribeToRatePlan subscribeToRatePlan = new SubscribeToRatePlan(productRatePlan.getId());
        CreateOrder createOrder = new CreateOrder("CreateSubscription", customerAccountNumber, initialTerm,
                renewalTerm, processingOptions, subscribeToRatePlan);

        String response = this.zuoraService.postJson("/v1/orders", createOrder);
        return response;
    }
}

package com.andy.data.api.zuora.request.order;

import com.andy.data.api.zuora.request.SubscribeToRatePlan;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateOrder {
    private String orderDate;
    private String existingAccountNumber;
    private List<CreateOrderSubscription> subscriptions;
    private ProcessingOptions processingOptions;

    public CreateOrder(String type, String customerAccountNumber, InitialTerm initialTerm,
                       RenewalTerm renewalTerm, ProcessingOptions processingOptions,
                       SubscribeToRatePlan subscribeToRatePlan) {
        String today = new SimpleDateFormat("YYYY-MM-dd").format(new Date());

        Terms terms = new Terms();
        terms.setInitialTerm(initialTerm);
        terms.setRenewalSetting("RENEW_WITH_SPECIFIC_TERM");
        terms.setRenewalTerms(Collections.singletonList(renewalTerm));

        CreateSubscription createSubscription = new CreateSubscription();
        createSubscription.setTerms(terms);
        createSubscription.setSubscribeToRatePlans(Collections.singletonList(subscribeToRatePlan));

        OrderAction orderAction = new OrderAction();
        orderAction.setType(type);
        orderAction.setCreateSubscription(createSubscription);
        orderAction.setTriggerDates(Arrays.asList(
                new TriggerDate("ContractEffective", today),
                new TriggerDate("ServiceActivation", today),
                new TriggerDate("CustomerAcceptance", today)
        ));
        CreateOrderSubscription createOrderSubscription = new CreateOrderSubscription(Collections.singletonList(orderAction));

        this.existingAccountNumber = customerAccountNumber;
        this.orderDate = today;
        this.subscriptions = Collections.singletonList(createOrderSubscription);
        this.processingOptions = processingOptions;
    }
}

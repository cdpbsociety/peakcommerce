package com.andy.data.api.zuora.request.order;

import com.andy.data.api.zuora.request.SubscribeToRatePlan;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateSubscription {
    private Terms terms;
    private List<SubscribeToRatePlan> subscribeToRatePlans;
}

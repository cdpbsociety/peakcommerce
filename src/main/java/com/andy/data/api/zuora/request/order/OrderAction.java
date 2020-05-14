package com.andy.data.api.zuora.request.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderAction {
    private String type;
    private List<TriggerDate> triggerDates;
    private CreateSubscription createSubscription;
}

package com.andy.data.api.zuora.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payment {
    private String accountId;
    private Double amount;
    private String currency;
    private String type;
    private String paymentMethodId;
    private String gatewayId;
}

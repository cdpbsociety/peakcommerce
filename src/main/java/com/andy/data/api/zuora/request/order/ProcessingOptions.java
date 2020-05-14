package com.andy.data.api.zuora.request.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ProcessingOptions {
    private boolean runBilling;
    private boolean collectPayment;
}

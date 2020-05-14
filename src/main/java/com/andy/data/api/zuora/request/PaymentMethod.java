package com.andy.data.api.zuora.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentMethod {
    private String type;
    private String accountKey;
    @JsonProperty("BAID")
    private String baid;
    private String email;
}

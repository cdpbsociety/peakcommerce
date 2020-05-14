package com.andy.data.api.zuora.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CreateAccount {
    @JsonProperty("AccountNumber")
    private String accountNumber;

    @JsonProperty("BillCycleDay")
    private Integer billCycleDay;

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Status")
    private String status;
}


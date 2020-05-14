package com.andy.data.api.zuora.request.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class RenewalTerm {
    private Integer period;
    private String periodType;
}

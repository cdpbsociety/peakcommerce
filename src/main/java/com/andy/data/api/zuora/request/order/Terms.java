package com.andy.data.api.zuora.request.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Terms {
    private InitialTerm initialTerm;
    private String renewalSetting;
    private List<RenewalTerm> renewalTerms;

}

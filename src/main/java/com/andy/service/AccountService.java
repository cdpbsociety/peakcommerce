package com.andy.service;

import com.andy.data.api.zuora.request.CreateAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AccountService {
    private final ZuoraService zuoraService;

    @Autowired
    public AccountService(ZuoraService zuoraService) {
        this.zuoraService = zuoraService;
    }

    public String create(CreateAccount createAccount) {
        String response = this.zuoraService.postJson("/v1/object/account", createAccount);
        return response;
    }

    public String activate(String accountId, String contactId) {
        Map body = Map.of(
                "Status", "Active",
                "PaymentTerm", "Due Upon Receipt",
                "Batch", "Batch1",
                "SoldToId", contactId,
                "BillToId", contactId
        );
        String response = this.zuoraService.put(String.format("/v1/object/account/%1$s", accountId), body);
        return response;
    }

}

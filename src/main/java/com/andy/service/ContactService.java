package com.andy.service;

import com.andy.data.api.zuora.request.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private final ZuoraService zuoraService;

    @Autowired
    public ContactService(ZuoraService zuoraService) {
        this.zuoraService = zuoraService;
    }

    public String create(Contact contact) {
        String response = this.zuoraService.postJson("/v1/object/contact", contact);
        return response;
    }


}

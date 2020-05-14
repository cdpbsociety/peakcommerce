package com.andy.controller;

import com.andy.controller.request.zuora.CreateOrderRequest;
import com.andy.controller.response.PaymentGateway;
import com.andy.controller.response.ProductRatePlan;
import com.andy.data.api.zuora.request.Contact;
import com.andy.data.api.zuora.request.CreateAccount;
import com.andy.data.api.zuora.request.Payment;
import com.andy.data.api.zuora.request.PaymentMethod;
import com.andy.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/zuora")
public class ZuoraController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final AccountService accountService;
    private final CatalogService catalogService;
    private final OrderService orderService;
    private final ContactService contactService;
    private final PaymentService paymentService;

    @Autowired
    public ZuoraController(AccountService inventoryService,
                           CatalogService catalogService,
                           OrderService orderService,
                           ContactService contactService,
                           PaymentService paymentService) {
        this.accountService = inventoryService;
        this.catalogService = catalogService;
        this.orderService = orderService;
        this.contactService = contactService;
        this.paymentService = paymentService;
    }

    @GetMapping

    /**
     * Creates a customer account in a Draft status
     * @return The response from the Zuora API or an error
     */
    @PostMapping("/account")
    public String createAccount() {
        // Note: This should be passed in by the caller in a real system.
        CreateAccount account = testCreateAccount();

        String response = accountService.create(account);
        return response;
    }

    /**
     * Creates a new Contact
     * @param accountId The Id of the customer account
     * @return The response from the Zuora API or an error
     */
    @PostMapping("/account/{id}/contact")
    public String createContact(@PathVariable("id") String accountId) {
        // Note: This should be passed in by the caller in a real system.
        Contact contact = testCreateContact(accountId);

        String response = contactService.create(contact);
        return response;
    }

    /**
     * Activates a customer account by updating the
     * Status, SoldToId, BillToId, Batch, and PaymentTerm
     * @param accountId The Id of the customer account
     * @param contactId The Id of the bill to and sell to contact
     * @return The response from the Zuora API or an error
     */
    @PutMapping("/account/{id}/activate")
    public String activateAccount(@PathVariable("id") String accountId, @RequestParam String contactId) {
        String response = accountService.activate(accountId, contactId);
        return response;
    }


    /**
     * Creates an order
     * @param createOrderRequest The request object with the rate plan name and customer account number
     * @return The response from the Zuora API or an error
     */
    @PostMapping("/order")
    public String createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        ProductRatePlan productRatePlan = catalogService.findProductRatePlan(createOrderRequest.getRatePlanName());
        String response = orderService.create(createOrderRequest.getAccountNumber(), productRatePlan);
        return response;
    }

    /**
     * Creates a PayPal payment method
     * @param accountId The Id of the customer account
     * @return The response from the Zuora API or an error
     */
    @PostMapping("/account/{id}/paymentMethod")
    public String paymentMethod(@PathVariable("id") String accountId) {
        // Note: This should be passed in by the caller in a real system.
        PaymentMethod paymentMethod = testPaymentMethod(accountId);

        String response = paymentService.createPaymentMethod(paymentMethod);
        return response;
    }

    /**
     * Creates a payment for this account (and invoice)
     * @param accountId The Id of the customer account
     * @return The response from the Zuora API or an error
     */
    @PostMapping("/account/{id}/payment")
    public String payment(@PathVariable("id") String accountId) {
        // Note: These should be passed in by the caller in a real system.
        PaymentGateway paymentGateway = paymentService.findPaymentGateway("PayPal");
        Payment payment = testPayment(accountId, paymentGateway);

        String response = paymentService.create(payment);
        return response;
    }

    /*
    Please note: This is not the way I would normally code this.
    These values should be passed in by the calling application as @RequestBody parameters.
     */
    private Contact testCreateContact(String accountId) {
        Contact contact = new Contact();
        contact.setAddress1("123 Main St.");
        contact.setCity("Calgary");
        contact.setState("Alberta");
        contact.setCountry("Canada");
        contact.setFirstName("Bob");
        contact.setLastName("Smith");
        contact.setAccountId(accountId);
        return contact;
    }

    private CreateAccount testCreateAccount() {
        CreateAccount createAccount = new CreateAccount();
        createAccount.setBillCycleDay(1);
        createAccount.setCurrency("USD");
        createAccount.setName("Test Customer Account for Andy");
        createAccount.setStatus("Draft");
        createAccount.setAccountNumber("AB123456789");
        return createAccount;
    }

    private PaymentMethod testPaymentMethod(String accountId) {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setAccountKey(accountId);
        paymentMethod.setType("PayPalEC");
        paymentMethod.setBaid("I-1TJ3GAGG82Y9");
        paymentMethod.setEmail("customer@example.com");

        return paymentMethod;
    }

    private Payment testPayment(String accountId, PaymentGateway paymentGateway) {
        Payment payment = new Payment();
        payment.setAccountId(accountId);
        payment.setAmount(5.99);
        payment.setCurrency("USD");
        payment.setType("Electronic");
        payment.setPaymentMethodId("2c92c0fb721269ea017213e5e63d21e6");
        payment.setGatewayId(paymentGateway.getId());
        return payment;
    }
}

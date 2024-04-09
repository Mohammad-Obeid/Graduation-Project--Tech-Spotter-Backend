package com.example.GradProJM.Controller;

import com.example.GradProJM.Model.PaymentMethods;
import com.example.GradProJM.Services.PaymentMethodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="paymentMethod")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping("")
    public List getPaymentMethods(){
        List <PaymentMethods> payments = paymentMethodService.getallPaymentMethods();
        return payments;
    }
}

package com.example.GradProJM.Services;

import com.example.GradProJM.Model.PaymentMethods;
import com.example.GradProJM.Repos.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {
    private static PaymentMethodRepository paymentMethodRepository;

    @Autowired
    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository=paymentMethodRepository;
    }

    public List<PaymentMethods> getallPaymentMethods() {
        return paymentMethodRepository.findAll();
    }
}

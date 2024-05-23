package com.example.GradProJM.Controller;

import com.example.GradProJM.Model.PaymentMethods;
import com.example.GradProJM.Services.PaymentMethodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{userID}/{paymentID}")
    public ResponseEntity<PaymentMethods> getPayment(@PathVariable("userID") int userID,
                                                     @PathVariable("paymentID") int paymentID
    ){
        Optional<PaymentMethods> payment = Optional.ofNullable(paymentMethodService.getpaymentmethod(userID,paymentID));
        return payment.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
    @PostMapping("authenticatetransaction/{orderID}")
    public ResponseEntity<Boolean> AuthenticatePayment(@PathVariable("orderID") int orderID){
        Optional<Boolean> paymentMethod = Optional.of(paymentMethodService.authenticatePaymetTransaction(
                orderID));
        return paymentMethod.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null));
    }
}

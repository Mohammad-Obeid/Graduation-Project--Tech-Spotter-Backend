package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Order;
import com.example.GradProJM.Model.PaymentMethods;
import com.example.GradProJM.Model.User;
import com.example.GradProJM.Repos.OrderRepository;
import com.example.GradProJM.Repos.PaymentMethodRepository;
import com.example.GradProJM.Repos.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final userRepository userRepo;
    private final OrderRepository orderRepo;
    private final EmailService mail;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository,
                                userRepository userRepo,
                                OrderRepository orderRepo,
                                EmailService mail) {
        this.paymentMethodRepository=paymentMethodRepository;
        this.userRepo=userRepo;
        this.orderRepo=orderRepo;
        this.mail=mail;
    }

    public List<PaymentMethods> getallPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethods getpaymentmethod(int userID, int paymentID) {
        Optional<User> user = userRepo.findByuserid(userID);
        if(user.isPresent()){
            Optional<PaymentMethods> payment = paymentMethodRepository.findById(
                    paymentID
            );
            if(payment.isPresent()){
                return payment.get();
            }
            return null;
        }
        return null;

    }

    public boolean authenticatePaymetTransaction(int orderID) {
        Optional<Order> order = orderRepo.findById(orderID);
        if(order.isPresent()){
            //todo: send authentication Link via mail
            return true;
        }
        return false;
    }
}

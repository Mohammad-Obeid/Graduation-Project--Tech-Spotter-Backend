package com.example.GradProJM.Repos;

import com.example.GradProJM.Controller.PaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethods,Integer> {
}

package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.PaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethods,Integer> {
}

package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.paymentMethodSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentSpecificationRepository extends JpaRepository<paymentMethodSpecification, Integer> {
}

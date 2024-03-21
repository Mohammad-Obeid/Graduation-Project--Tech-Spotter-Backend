package com.example.GradProJM.Repos;


import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional<Customer> findCustomerByCustID(int custID);



}
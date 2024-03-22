package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.User;
import com.example.GradProJM.Repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class customerService {
    private static CustomerRepository custRepo;

    @Autowired
    public customerService(CustomerRepository custRepo) {
        this.custRepo = custRepo;
    }

    public List<Customer> getCustomers(){
        return  custRepo.findAll();
    }

    public static void addNewCustomer(Customer customer) {
        Optional<Customer> findBycustid=custRepo.findCustomerByCustID(customer.getCustID());
        if(findBycustid.isPresent()){
            throw new IllegalStateException("Email Taken from Customer ");
        }
        else {
            custRepo.save(customer);
        }
    }

    public Customer getCustomerbyId(int custID) {
        Optional<Customer> findBycustid=custRepo.findCustomerByCustID(custID);
        return findBycustid.orElse(null);
    }

}
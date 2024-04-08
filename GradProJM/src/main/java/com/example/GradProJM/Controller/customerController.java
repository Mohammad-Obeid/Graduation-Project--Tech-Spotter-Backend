package com.example.GradProJM.Controller;
import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.User;
import com.example.GradProJM.Services.customerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path="customer")
@RestController
public class customerController {
    private final customerService customerService;


    @Autowired
    public customerController(customerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping
    public List<Customer> std(){
        return customerService.getCustomers();
    }

    @PostMapping
    public void addNewCustomer(@RequestBody Customer customer){
        customerService.addNewCustomer(customer);
    }

    @GetMapping("/{id}")
    public Customer getCustomerbyID(@PathVariable("id") int custID){
        Customer cust=customerService.getCustomerbyId(custID);
        return cust;
    }
}

package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.Order;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.OrderRepository;
import com.example.GradProJM.Repos.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepo;

    private final CustomerRepository custRepo;

    private final ProductRepository prodRepo;



    public OrderService(OrderRepository orderRep, CustomerRepository custRepo,
                        ProductRepository prodRepo) {
        this.orderRepo = orderRep;
        this.custRepo = custRepo;
        this.prodRepo = prodRepo;
    }

    public List getOrdersForACustomer(int custID) {
        Optional<Customer> customer = custRepo.findCustomerByCustID(custID);
        if(customer.isPresent()){
            Optional<List> orders = orderRepo.findOrdersByCustomer_CustID(custID);
            return orders.get();
        }
        return null;
    }
}

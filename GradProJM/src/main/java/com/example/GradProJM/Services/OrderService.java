package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.Order;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.OrderRepository;
import com.example.GradProJM.Repos.ProductRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepo;

    private final CustomerRepository custRepo;

    private final ProductRepository prodRepo;



    public OrderService(OrderRepository orderRepo, CustomerRepository custRepo,
                        ProductRepository prodRepo) {
        this.orderRepo = orderRepo;
        this.custRepo = custRepo;
        this.prodRepo = prodRepo;
    }

    public List getOrdersForACustomer(int custID) {
        Optional<Customer> customer = custRepo.findCustomerByCustID(custID);
        if(customer.isPresent()){
            Optional<List<Order>> orders = orderRepo.findOrdersByCustomer_CustID(custID);
            return orders.get();
        }
        return null;
    }

    public Order updateOrderStatus(int orderID, Order ord) {
        Optional<Order> order = orderRepo.findById(orderID);
        if(order.isPresent()){
            order.get().setStatus(ord.getStatus());
            orderRepo.save(order.get());
            return order.get();
        }
        return null;

    }

    public Order updateOrderLocation(int orderID, Order ord) {
        Optional<Order> order = orderRepo.findById(orderID);
        if(order.isPresent()){
            order.get().setOrderAdd(ord.getOrderAdd());
            orderRepo.save(order.get());
            return order.get();
        }
        return null;
    }
}

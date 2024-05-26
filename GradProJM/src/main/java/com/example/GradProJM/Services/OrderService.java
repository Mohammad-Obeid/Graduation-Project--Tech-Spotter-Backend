package com.example.GradProJM.Services;

import com.example.GradProJM.Model.*;
import com.example.GradProJM.Repos.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepo;

    private final CustomerRepository custRepo;

    private final ProductRepository prodRepo;
    private final ShopOwnerRepository shpRepo;
    private final orderItemRepository ordItmRepo;



    public OrderService(OrderRepository orderRepo,
                        CustomerRepository custRepo,
                        ProductRepository prodRepo,
                        ShopOwnerRepository shpRepo,
                        orderItemRepository ordItmRepo) {
        this.orderRepo = orderRepo;
        this.custRepo = custRepo;
        this.prodRepo = prodRepo;
        this.shpRepo=shpRepo;
        this.ordItmRepo=ordItmRepo;
    }

    public List getOrdersForACustomer(int custID, int pageNum) {
        Optional<Customer> customer = custRepo.findCustomerByCustID(custID);
        if(customer.isPresent()){
            Optional<List<Order>> orders = orderRepo.findOrdersByCustomer_CustID(custID,PageRequest.of(pageNum,2));
            return orders.get();
        }
        return null;
    }

    public List getOrdersForAShop(int shopID, int pageNum) {
        Optional<ShopOwner> shop = shpRepo.findById(shopID);
        if(shop.isPresent()){
            Optional<List<orderItems>> orders = ordItmRepo.findAllByProduct_ShopShopID(shopID,PageRequest.of(pageNum,2));
            return orders.get();
        }
        return null;
    }


    public Order updateOrderStatus(int orderID, Order ord) {
        Optional<Order> order = orderRepo.findById(orderID);
        if(order.isPresent()){
            order.get().setStatus(ord.getStatus());
            orderRepo.save(order.get());
            return order.orElse(null);
        }
        return null;

    }

    public Order updateOrderLocation(int orderID, Order ord) {
        Optional<Order> order = orderRepo.findById(orderID);
        if(order.isPresent()){
            order.get().setOrderAdd(ord.getOrderAdd());
            orderRepo.save(order.get());
            return order.orElse(null);
        }
        return null;
    }


}

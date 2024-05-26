package com.example.GradProJM.Controller;

import com.example.GradProJM.Model.Order;
import com.example.GradProJM.Services.OrderService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("getordersforCust/{custID}/{pageNum}")
    public ResponseEntity<List> getOrdersForACustomer(@PathVariable("custID") int custID, @PathVariable("pageNum") int pageNum){
        Optional<List> orders= Optional.ofNullable(orderService.getOrdersForACustomer(custID, pageNum));
        return orders.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @GetMapping("getordersforShop/{shopID}/{pageNum}")
    public ResponseEntity<List> getOrdersForAShop(@PathVariable("shopID") int shopID, @PathVariable("pageNum") int pageNum){
        Optional<List> orders= Optional.ofNullable(orderService.getOrdersForAShop(shopID, pageNum));
        return orders.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }



    @PatchMapping("updateStatus/{orderID}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable("orderID") int orderID,@RequestBody Order ord){
        Optional<Order> order= Optional.ofNullable(orderService.updateOrderStatus(orderID, ord));
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PatchMapping("updateLocation/{orderID}")
    public ResponseEntity<Order> updateOrderLocation(@PathVariable("orderID") int orderID,@RequestBody Order ord){
        Optional<Order> order= Optional.ofNullable(orderService.updateOrderLocation(orderID, ord));
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
}

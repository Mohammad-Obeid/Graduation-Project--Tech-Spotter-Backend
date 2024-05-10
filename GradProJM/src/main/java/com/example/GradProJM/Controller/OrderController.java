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

    @GetMapping("getorders/{custID}")
    public ResponseEntity<List> getOrdersForACustomer(@PathVariable("custID") int custID){
        Optional<List> orders= Optional.ofNullable(orderService.getOrdersForACustomer(custID));
        return orders.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
}

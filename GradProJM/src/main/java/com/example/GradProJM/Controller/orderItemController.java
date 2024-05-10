package com.example.GradProJM.Controller;


import com.example.GradProJM.Model.Order;
import com.example.GradProJM.Model.orderItems;
import com.example.GradProJM.Services.orderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("orderitem")
public class orderItemController {
    private final orderItemService ordItmSrv;

    public orderItemController(orderItemService ordItmSrv) {
        this.ordItmSrv = ordItmSrv;
    }

    @PostMapping("makeNewOrder")
    public ResponseEntity<Order> makeNewOrder(@RequestBody Order order){
        Optional<Order> ord= Optional.ofNullable(ordItmSrv.MakeNewOrder(order));
        return ord.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));

    }

}

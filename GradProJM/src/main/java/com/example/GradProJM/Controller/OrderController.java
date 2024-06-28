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

    @GetMapping("getNumOfPagesForCustomerOrders/{custID}")
    public int getNumberOfPagesForCustomerOrders(@PathVariable("custID") int custID
    ){
        int num= orderService.getNumberOfPagesForCustomerOrders(custID);
        return num;
    }

    @GetMapping("getAllordersforShop/{shopID}/{pageNum}")
    public ResponseEntity<List> getAllOrdersForAShop(@PathVariable("shopID") int shopID, @PathVariable("pageNum") int pageNum){
        Optional<List> orders= Optional.ofNullable(orderService.getAllOrdersForAShop(shopID, pageNum));
        return orders.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @GetMapping("getNumOfPagesForAllShopOrders/{ShopName}")
    public int getNumberOfPagesForAllShopOrders(@PathVariable("ShopName") String ShopName
    ){
        int num= orderService.getNumberOfPagesForAllShopOrders(ShopName);
        return num;
    }

    @GetMapping("getAcceptedordersforShop/{shopID}/{pageNum}")
    public ResponseEntity<List> getAcceptedOrdersForAShop(@PathVariable("shopID") int shopID, @PathVariable("pageNum") int pageNum){
        Optional<List> orders= Optional.ofNullable(orderService.getAcceptedOrdersForAShop(shopID, pageNum));
        return orders.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @GetMapping("getNumOfPagesForAcceptedShopOrders/{ShopName}")
    public int getNumOfPagesForAcceptedShopOrders(@PathVariable("ShopName") String ShopName
    ){
        int num= orderService.getNumOfPagesForAcceptedShopOrders(ShopName);
        return num;
    }




    @GetMapping("getShippedordersforShop/{shopID}/{pageNum}")
    public ResponseEntity<List> getShippedOrdersForAShop(@PathVariable("shopID") int shopID, @PathVariable("pageNum") int pageNum){
        Optional<List> orders= Optional.ofNullable(orderService.getShippedOrdersForAShop(shopID, pageNum));
        return orders.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
    @GetMapping("getNumOfPagesForShippedShopOrders/{ShopName}")
    public int getNumOfPagesForShippedShopOrders(@PathVariable("ShopName") String ShopName
    ){
        int num= orderService.getNumOfPagesForShippedShopOrders(ShopName);
        return num;
    }




    @GetMapping("getPendingordersforShop/{shopID}/{pageNum}")
    public ResponseEntity<List> getPendingOrdersForAShop(@PathVariable("shopID") int shopID, @PathVariable("pageNum") int pageNum){
        Optional<List> orders= Optional.ofNullable(orderService.getPendingOrdersForAShop(shopID, pageNum));
        return orders.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
    @GetMapping("getNumOfPagesForPendingShopOrders/{ShopName}")
    public int getNumOfPagesForPendingShopOrders(@PathVariable("ShopName") String ShopName
    ){
        int num= orderService.getNumOfPagesForPendingShopOrders(ShopName);
        return num;
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

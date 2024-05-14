package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.Order;
import com.example.GradProJM.Model.Shop_Products;
import com.example.GradProJM.Model.orderItems;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.OrderRepository;
import com.example.GradProJM.Repos.orderItemRepository;
import com.example.GradProJM.Repos.productShopRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class orderItemService {
    private final orderItemRepository ordItmRepo;
    private final OrderRepository ordRepo;
    private final productShopRepository prdshpRepo;
    private final CustomerRepository custRepo;


    public orderItemService(orderItemRepository ordItmRepo,
                            OrderRepository ordRepo,
                            productShopRepository prdshpRepo,
                            CustomerRepository custRepo) {
        this.ordItmRepo = ordItmRepo;
        this.ordRepo=ordRepo;
        this.prdshpRepo=prdshpRepo;
        this.custRepo=custRepo;
    }



//    public orderItems MakeNewOrder(orderItems orderItem) {
//        Order order=new Order();
//        order.setOrderDate(String.valueOf(LocalDate.now()));
//        order.setCustomer(orderItem.getOrder().getCustomer());
//        order.setOrderAdd(orderItem.getOrder().getOrderAdd());
//        ordRepo.save(order);
//        Optional<Shop_Products> shopProduct = prdshpRepo.findShop_ProductsByShop_ShopIDAndProduct_ProductId(
//                orderItem.getProducts().getShop().getShopID(),
//                orderItem.getProducts().getProduct().getProductId());
//        orderItem.setProducts(shopProduct.get());
//        orderItem.setOrder(order);
//        ordRepo.save(order);
//        shopProduct.ifPresent(orderItem::setProducts);
//        System.out.println("///////////////////////////////////////////////////////////////////////////////");
//        System.out.println(shopProduct.get().getId());
////order.set
////        ordItmRepo.saveAndFlush(orderItem);
//        return orderItem;
//
//
//
//
//    }


    public Order MakeNewOrder(Order order) {
        Order ord=new Order();
        Optional<Customer> cust = custRepo.findCustomerByCustID(order.getCustomer().getCustID());
        ord.setCustomer(cust.get());
        ord.setOrderAdd(order.getOrderAdd());
//        ord.setCustomer(order.getCustomer());
        ord.setOrderDate(String.valueOf(LocalDate.now()));
        List<Shop_Products> ll = new ArrayList<>();
        List<orderItems> orderItems = new ArrayList<>();
        for(int i=0;i<order.getOrderItem().size();i++){
            Optional<Shop_Products> shopProduct = prdshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcode(
                    order.getOrderItem().get(i).getProduct().getShop().getShopName(),
                    order.getOrderItem().get(i).getProduct().getProduct().getProductBarcode());
            if(shopProduct.get().getQuantity()>0){
//              todo:  shopProduct.get().setQuantity(shopProduct.get().getQuantity()-1); after payment verification
                orderItems orditm=new orderItems();
                orditm.setOrder(ord);
                orditm.setProduct(shopProduct.get());
                orderItems.add(orditm);
            }
            else{
                System.out.println("Product: "+shopProduct.get().getProduct().getProductName()+" is Out of Stock");
            }
        }
        ord.setStatus("Submitted");
        ord.setOrderItem(orderItems);
        ordRepo.save(ord);
        return ord;
    }

//    public Order MakeNewOrder(int custID, String shopName, String prodBarcode, Order order) {
//        List<>
//    }


//    public orderItems MakeNewOrder(orderItems orderItem) {
//
//        Order order = new Order();
//        order.setOrderDate(String.valueOf(LocalDate.now()));
//        order.setCustomer(orderItem.getOrder().getCustomer());
//        order.setOrderAdd(orderItem.getOrder().getOrderAdd());
//        order.setOrderID(orderItem.getOrderItemID());
//        // Save the order
////        ordRepo.save(order);
//        System.out.println(orderItem.getOrder().toString());
//
////        Optional<Shop_Products> shopProduct = prdshpRepo.findShop_ProductsByShop_ShopIDAndProduct_ProductId(
////                orderItem.getProducts().getShop().getShopID(),
////                orderItem.getProducts().getProduct().getProductId());
////
////        shopProduct.ifPresent(orderItem::setProducts);
////        orderItem.setOrder(order);
////
////        ordItmRepo.saveAndFlush(orderItem);
//        return orderItem;
//    }


}

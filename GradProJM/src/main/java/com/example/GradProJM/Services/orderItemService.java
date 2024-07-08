package com.example.GradProJM.Services;

import com.example.GradProJM.Model.*;
import com.example.GradProJM.Repos.*;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class orderItemService {
    private final orderItemRepository ordItmRepo;
    private final OrderRepository ordRepo;
    private final productShopRepository prdshpRepo;
    private final CustomerRepository custRepo;
    private final userRepository userRepo;
    private final ShopOwnerRepository shopRepo;


    public orderItemService(orderItemRepository ordItmRepo,
                            OrderRepository ordRepo,
                            productShopRepository prdshpRepo,
                            CustomerRepository custRepo, userRepository userRepo, ShopOwnerRepository shopRepo) {
        this.ordItmRepo = ordItmRepo;
        this.ordRepo=ordRepo;
        this.prdshpRepo=prdshpRepo;
        this.custRepo=custRepo;
        this.userRepo = userRepo;
        this.shopRepo = shopRepo;
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
        //todo: kadoumi order modification,,,,, important.............

        Optional<User> cust = userRepo.findById(order.getUser().getUserid());
        ord.setUser(cust.get());
        ord.setAddress(order.getAddress());
//        ord.setCustomer(order.getCustomer());
        ord.setOrderDate(String.valueOf(LocalDate.now()));
        List<Shop_Products> ll = new ArrayList<>();
        List<orderItems> orderItems = new ArrayList<>();
        double totprice=0;
        for(int i=0;i<order.getOrderItem().size();i++){

            Optional<Shop_Products> shopProduct = prdshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcodeAndAndDeletedFalse(
                    order.getOrderItem().get(i).getProduct().getShop().getShopName(),
                    order.getOrderItem().get(i).getProduct().getProduct().getProductBarcode());
            if(shopProduct.isPresent() && shopProduct.get().getQuantity()>=order.getOrderItem().get(i).getItemQuantity()){
//              todo:  shopProduct.get().setQuantity(shopProduct.get().getQuantity()-order.getOrderItem().get(i).getItemQuantity()); after payment verification
                orderItems orditm=new orderItems();
                orditm.setOrder(ord);
                orditm.setProduct(shopProduct.get());
                orditm.setItemQuantity(order.getOrderItem().get(i).getItemQuantity());
                orditm.setOrderItemStats("Pending");
                orderItems.add(orditm);
                totprice+=shopProduct.get().getProductPrice();
            }
            else{
                System.out.println("Product: "+shopProduct.get().getProduct().getProductName()+" is Out of Stock");
            }
        }
        ord.setStatus("Processing");
        ord.setTotPrice(totprice);
        ord.setOrderItem(orderItems);
        ordRepo.save(ord);
        return ord;
    }
    public void changeOrderStatus(){
        Optional <List<Order>> orders = ordRepo.findOrdersByStatus("Processing");
        for(int i = 0; i < orders.get().size(); i++){
            Optional<List<orderItems>> items = ordItmRepo.findByOrder(orders.get().get(i));
            int flag=0;
            for(int j=0; j < items.get().size(); j++){
                if(!items.get().get(j).getOrderItemStats().equals("Accepted")){
                    //todo: check
                    flag=1;
                }
                if(flag==1)break;
            }
            if(flag==0){
                orders.get().get(i).setStatus("Ready to be Shipped");
                ordRepo.save(orders.get().get(i));
            }
        }
    }



    public void changeOrderStatus2(){
        Optional <List<Order>> orders = ordRepo.findOrdersByStatus("Ready to be Shipped");
        for(int i = 0; i < orders.get().size(); i++){
            Optional<List<orderItems>> items = ordItmRepo.findByOrder(orders.get().get(i));
            int flag=0;
            for(int j=0; j < items.get().size(); j++){
                if(!items.get().get(j).getOrderItemStats().equals("Shipped")){
                    //todo: check
                    flag=1;
                }
                if(flag==1)break;
            }
            if(flag==0){
                orders.get().get(i).setStatus("Shipped");
                ordRepo.save(orders.get().get(i));
            }
        }
    }




    public orderItems changeStatus(int orderID, int prodID,String status) {
        Optional<orderItems> item = ordItmRepo.findByOrderOrderIDAndProductId(orderID,prodID);
        item.get().setOrderItemStats(status);
        ordItmRepo.save(item.get());
        return item.get();
    }

    public List<Sales> getMonthlySales(int userID, String month) {
        Optional<User> user = userRepo.findById(userID);
            if(user.isPresent() && user.get().getStatus()==1) {
                Optional<ShopOwner> shop = shopRepo.findById(user.get().getShopowner().getShopID());
                List<Sales> sales = new ArrayList<>();
                for (int j = 1; j < 30; j++) {
                    String date = "";
                    if(j<10) date = "2024-"+month+"-"+"0"+j;
                    else date = "2024-"+month+"-"+j;
                    Optional<List<orderItems>> items = ordItmRepo.findByProductShopShopNameAndOrderOrderDate(shop.get().getShopName(), date);
                    if(!items.get().isEmpty()) {
                        for (int i = 0; i < items.get().size(); i++) {
                            Optional<Order> order = ordRepo.findById(items.get().get(i).getOrder().getOrderID());

                            LocalDate orderDate = LocalDate.parse(order.get().getOrderDate());

                            if (!sales.isEmpty() && MonthDay.from(orderDate).getDayOfMonth() == sales.get(sales.size() - 1).getDay()) {
                                double x = sales.get(sales.size() - 1).getTotalPrice() + items.get().get(i).getProduct().getProductPrice();
                                sales.get(sales.size() - 1).setTotalPrice(x);
                            } else {
                                Sales s = new Sales();
                                s.setDay(MonthDay.from(orderDate).getDayOfMonth());
                                s.setTotalPrice(items.get().get(i).getProduct().getProductPrice());
                                sales.add(s);
                            }

                        }

                    }
                }
                return sales;
            }
            return null;

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

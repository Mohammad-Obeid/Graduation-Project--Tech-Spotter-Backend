package com.example.GradProJM.Model;
import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;
    private String orderDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Shop_Products> products;

    @OneToMany(mappedBy = "order",cascade=CascadeType.ALL)

    private List<orderItems> orderItem;


//    @OneToOne(cascade = CascadeType.ALL)
    private String address;
    private String status;

    private double totPrice=0;

    public Order() {
    }

    public Order(int orderID, String orderDate, String orderAdd,String status) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.status=status;
    }

    public Order(String orderDate, String orderAdd,String status) {
        this.orderDate = orderDate;
        this.status=status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(double totPrice) {
        this.totPrice = totPrice;
    }
    //    public List<Shop_Products> getProducts() {
//        return products;
//    }
//
//    public void setProducts(List<Shop_Products> products) {
//        this.products = products;
//    }


//    public List<orderItems> getOrderItem() {
//        return orderItem;
//    }
//
//    public void setOrderItem(List<orderItems> orderItem) {
//        this.orderItem = orderItem;
//    }


    public List<orderItems> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<orderItems> orderItem) {
        this.orderItem = orderItem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}

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
    private String orderAdd;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Customer customer;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Shop_Products> products;

    @OneToMany(mappedBy = "order",cascade=CascadeType.ALL)
    private List<orderItems> orderItem;
    public Order() {
    }

    public Order(int orderID, String orderDate, String orderAdd) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.orderAdd = orderAdd;
    }

    public Order(String orderDate, String orderAdd) {
        this.orderDate = orderDate;
        this.orderAdd = orderAdd;
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


    public String getOrderAdd() {
        return orderAdd;
    }

    public void setOrderAdd(String orderAdd) {
        this.orderAdd = orderAdd;
    }



    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", orderDate='" + orderDate + '\'' +
                ", Address='" + orderAdd + '\'' +
                '}';
    }
}

package com.example.GradProJM.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orderitems")
public class orderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemID;

    @ManyToOne(cascade = CascadeType.ALL)
    private Shop_Products product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Order order;

    public orderItems() {
    }

    public orderItems(int orderItemID) {
        this.orderItemID = orderItemID;
    }

    public int getOrderItemID() {
        return orderItemID;
    }

    public void setOrderItemID(int orderItemID) {
        this.orderItemID = orderItemID;
    }

    public Shop_Products getProduct() {
        return product;
    }

    public void setProduct(Shop_Products product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    @Override
    public String toString() {
        return "orderItems{" +
                "orderItemID=" + orderItemID +
                ", order=" + order +
                '}';
    }
}

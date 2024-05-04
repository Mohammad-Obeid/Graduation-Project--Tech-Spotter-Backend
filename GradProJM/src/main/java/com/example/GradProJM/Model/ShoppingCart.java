package com.example.GradProJM.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "shoppingcart")

public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int cartID;
//    @OneToOne(cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<product> products;

    private double totalPrice;

    public ShoppingCart() {}

    public ShoppingCart(int cartID, double totalPrice) {
        this.cartID = cartID;
        this.totalPrice = totalPrice;
    }
    public ShoppingCart(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }

    public List<product> getProducts() {
        return products;
    }

    public void setProducts(List<product> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        double totPrice=0;
        for(int i=0; i<products.size();i++){
            totPrice+=products.get(i).getProductPrice();
        }
        return totPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

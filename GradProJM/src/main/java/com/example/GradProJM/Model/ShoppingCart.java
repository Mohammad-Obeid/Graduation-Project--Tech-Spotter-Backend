package com.example.GradProJM.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "shoppingcart")

public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartID;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Shop_Products> products;

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


    public List<Shop_Products> getProducts() {
        return products;
    }

    public void setProducts(List<Shop_Products> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        double totPrice=0;
        for(int i=0; i<products.size();i++){
            totPrice+=products.get(i).getProduct().getProductPrice();
        }
        return totPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

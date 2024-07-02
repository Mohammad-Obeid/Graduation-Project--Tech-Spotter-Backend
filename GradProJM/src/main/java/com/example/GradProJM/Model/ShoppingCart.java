package com.example.GradProJM.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "shoppingcart")

public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartID;
    //todo: change to another class, to contain quantity!
    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItems> cartItems;



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

    public List<CartItems> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItems> cartItems) {
        this.cartItems = cartItems;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    public double getTotalPrice() {
        double totPrice=0;
        for(int i=0; i<cartItems.size();i++){
            totPrice+=cartItems.get(i).getProductTotalPrice();
        }
        return totPrice;
    }

}

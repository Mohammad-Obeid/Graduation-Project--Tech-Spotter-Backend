package com.example.GradProJM.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "cartitems")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    private Shop_Products product;

    private int quantity;
    private double productTotalPrice;
    @JoinColumn(name = "cartID")
    private int cartID;

    public CartItems() {
    }

    public CartItems(int id, Shop_Products product, int quantity, double productTotalPrice) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.productTotalPrice = productTotalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Shop_Products getProduct() {
        return product;
    }

    public void setProduct(Shop_Products product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getProductTotalPrice() {
        return productTotalPrice;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public void setProductTotalPrice(double productTotalPrice) {
        this.productTotalPrice = quantity*product.getProductPrice();
    }
}

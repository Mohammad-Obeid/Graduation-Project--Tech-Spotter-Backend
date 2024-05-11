package com.example.GradProJM.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "wishlist")
public class wishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishListID;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Shop_Products> products;

    public wishList() {
    }

    public wishList(int wishListID) {
        this.wishListID = wishListID;
    }

    public int getWishListID() {
        return wishListID;
    }

    public void setWishListID(int wishListID) {
        this.wishListID = wishListID;
    }

    public List<Shop_Products> getProducts() {
        return products;
    }

    public void setProducts(List<Shop_Products> products) {
        this.products = products;
    }
}

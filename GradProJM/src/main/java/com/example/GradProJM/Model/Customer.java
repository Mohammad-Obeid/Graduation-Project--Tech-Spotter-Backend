package com.example.GradProJM.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int custID;
    private String FName,LName;
    private String BDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;

    @OneToOne(cascade = CascadeType.ALL)
    private wishList wishlist;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
    private List<Order> orders;

    public Customer() {
    }

    public Customer(int custID, String FName, String LName, String BDate) {
        this.custID = custID;
        this.FName = FName;
        this.LName = LName;
        this.BDate = BDate;
    }

    public Customer(String FName, String LName, String BDate, User user) {
        this.FName = FName;
        this.LName = LName;
        this.BDate = BDate;
        this.user=user;
    }



    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getBDate() {
        return BDate;
    }

    public void setBDate(String BDate) {
        this.BDate = BDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public wishList getWishlist() {
        return wishlist;
    }

    public void setWishlist(wishList wishlist) {
        this.wishlist = wishlist;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custID=" + custID +
                ", FName='" + FName + '\'' +
                ", LName='" + LName + '\'' +
                ", BDate=" + BDate +
                '}';
    }
}

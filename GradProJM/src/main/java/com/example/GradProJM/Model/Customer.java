package com.example.GradProJM.Model;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "customer")
public class Customer{
    @Id
//    @SequenceGenerator(
//            name = "customer_sequence",
//            sequenceName = "customer_sequence",
//            allocationSize = 1
//    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int custID;
    private String FName,LName;
    private String BDate;
    private int CartID;

    @OneToOne
    private User user;

    public Customer() {
    }

    public Customer(int custID, String FName, String LName, String BDate, int cartID, int usrID) {
        this.custID = custID;
        this.FName = FName;
        this.LName = LName;
        this.BDate = BDate;
        this.CartID = cartID;
    }

    public Customer(String FName, String LName, String BDate, int cartID) {
        this.FName = FName;
        this.LName = LName;
        this.BDate = BDate;
        this.CartID = cartID;
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

    public int getCartID() {
        return CartID;
    }

    public void setCartID(int cartID) {
        this.CartID = cartID;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custID=" + custID +
                ", FName='" + FName + '\'' +
                ", LName='" + LName + '\'' +
                ", BDate=" + BDate +
                ", CartID=" + CartID +
                '}';
    }
}

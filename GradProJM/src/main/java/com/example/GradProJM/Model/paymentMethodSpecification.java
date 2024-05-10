package com.example.GradProJM.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "paymentSpecification")
public class paymentMethodSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentSpecID;
    private String cardNum,expireDate,securityCode, ownerName;

    @OneToOne(cascade=CascadeType.ALL)
    @JsonIgnore
    private PaymentMethods paymentMethod;


    public paymentMethodSpecification() {
    }

    public paymentMethodSpecification(int paymentSpecID, String cardNum, String expireDate, String securityCode, String ownerName) {
        this.paymentSpecID = paymentSpecID;
        this.cardNum = cardNum;
        this.expireDate = expireDate;
        this.securityCode = securityCode;
        this.ownerName = ownerName;
    }

    public paymentMethodSpecification(String cardNum, String expireDate, String securityCode, String ownerName) {
        this.cardNum = cardNum;
        this.expireDate = expireDate;
        this.securityCode = securityCode;
        this.ownerName = ownerName;
    }

    public int getPaymentSpecID() {
        return paymentSpecID;
    }

    public void setPaymentSpecID(int paymentSpecID) {
        this.paymentSpecID = paymentSpecID;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public PaymentMethods getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethods paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "paymentMethodSpecification{" +
                "paymentSpecID=" + paymentSpecID +
                ", cardNum='" + cardNum + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }
}

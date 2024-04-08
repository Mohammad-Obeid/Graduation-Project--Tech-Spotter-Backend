package com.example.GradProJM.Controller;


import com.example.GradProJM.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
@Table(name="paymentmethod")
public class PaymentMethods {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private int paymentMethodId;
    private String paymentMethodName;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;

    public PaymentMethods() {
    }

    public PaymentMethods(int paymentMethodId, String paymentMethodName) {
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodName = paymentMethodName;
    }
    public PaymentMethods(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PaymentMethods{" +
                "paymentMethodId=" + paymentMethodId +
                ", PaymentMethodName='" + paymentMethodName + '\'' +
                ", user=" + user +
                '}';
    }
}

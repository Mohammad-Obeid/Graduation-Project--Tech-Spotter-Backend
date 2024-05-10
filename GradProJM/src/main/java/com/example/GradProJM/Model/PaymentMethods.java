package com.example.GradProJM.Model;


import com.example.GradProJM.Model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
@Table(name="paymentmethod")
public class PaymentMethods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentMethodId;
    private String paymentMethodName;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JsonIgnore
    User user;
    @OneToOne(cascade = CascadeType.ALL)
    private paymentMethodSpecification paymentMethodSpecification;
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

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public paymentMethodSpecification getPaymentMethodSpecification() {
        return paymentMethodSpecification;
    }

    public void setPaymentMethodSpecification(paymentMethodSpecification paymentMethodSpecification) {
        this.paymentMethodSpecification = paymentMethodSpecification;
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

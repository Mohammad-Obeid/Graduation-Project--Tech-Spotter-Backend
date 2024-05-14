package com.example.GradProJM.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "customerRates")
public class customerRates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rateFeedbackID;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Customer customer;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Shop_Products products;

    private double rate;
    private String feedBackContent;

    public customerRates() {
    }

    public customerRates(int rateFeedbackID, double rate, String feedBackContent) {
        this.rateFeedbackID = rateFeedbackID;
        this.rate = rate;
        this.feedBackContent = feedBackContent;
    }
    public customerRates(double rate, String feedBackContent) {
        this.rate = rate;
        this.feedBackContent = feedBackContent;
    }

    public int getRateFeedbackID() {
        return rateFeedbackID;
    }

    public void setRateFeedbackID(int rateFeedbackID) {
        this.rateFeedbackID = rateFeedbackID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Shop_Products getProducts() {
        return products;
    }

    public void setProducts(Shop_Products products) {
        this.products = products;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getFeedBackContent() {
        return feedBackContent;
    }

    public void setFeedBackContent(String feedBackContent) {
        this.feedBackContent = feedBackContent;
    }
}

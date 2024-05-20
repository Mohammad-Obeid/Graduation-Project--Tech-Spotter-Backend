package com.example.GradProJM.Model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

@Entity
@Table(name = "reports")
@JsonSerialize(using = ReportSerializer.class)
public class ProductReport{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String reportTitle ;
    private String reportContent;

    @ManyToOne(cascade = CascadeType.ALL)
    private Customer cust;

    @ManyToOne(cascade = CascadeType.ALL)
    private Shop_Products product;

    private String reportStatus;
    public ProductReport() {
    }

    public ProductReport(int id, String reportTitle, String reportContent, Customer cust) {
        this.id = id;
        this.reportTitle = reportTitle;
        this.reportContent = reportContent;
        this.cust = cust;
    }

    public ProductReport(String reportTitle, String reportContent, Customer cust) {
        this.reportTitle = reportTitle;
        this.reportContent = reportContent;
        this.cust = cust;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public Customer getCust() {
        return cust;
    }

    public void setCust(Customer cust) {
        this.cust = cust;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Shop_Products getProduct() {
        return product;
    }

    public void setProduct(Shop_Products product) {
        this.product = product;
    }
}


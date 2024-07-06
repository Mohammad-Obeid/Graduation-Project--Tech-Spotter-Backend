package com.example.GradProJM.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="search_query_table")
public class SearchQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String query;
    private LocalDateTime searchDate;

    private int custID;
    private int numOfClicks=0;


    public SearchQuery() {
    }

    public SearchQuery(String query, LocalDateTime searchDate, int custID) {
        this.query = query;
        this.searchDate = searchDate;
        this.custID=custID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public LocalDateTime getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(LocalDateTime searchDate) {
        this.searchDate = searchDate;
    }

    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }

    public int getNumOfClicks() {
        return numOfClicks;
    }

    public void setNumOfClicks(int numOfClicks) {
        this.numOfClicks = numOfClicks;
    }

}


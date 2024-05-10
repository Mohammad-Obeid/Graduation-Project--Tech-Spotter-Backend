package com.example.GradProJM.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addID;
    @NonNull
    private String governorate, city, town, streetNo, depNo, moreDetails;

    @ManyToOne(cascade=CascadeType.ALL)
    @JsonBackReference
    @JsonIgnore
    User user;

    public Address() {}

    public Address(int addID, @NonNull String governorate, @NonNull String city, @NonNull String town, @NonNull String streetNo, @NonNull String depNo, @NonNull String moreDetails) {
        this.addID = addID;
        this.governorate = governorate;
        this.city = city;
        this.town = town;
        this.streetNo = streetNo;
        this.depNo = depNo;
        this.moreDetails = moreDetails;
    }
    public Address(@NonNull String governorate, @NonNull String city, @NonNull String town, @NonNull String streetNo, @NonNull String depNo, @NonNull String moreDetails) {
        this.governorate = governorate;
        this.city = city;
        this.town = town;
        this.streetNo = streetNo;
        this.depNo = depNo;
        this.moreDetails = moreDetails;
    }

    public int getAddID() {
        return addID;
    }

    public void setAddID(int addID) {
        this.addID = addID;
    }

    @NonNull
    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(@NonNull String governorate) {
        this.governorate = governorate;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    @NonNull
    public String getTown() {
        return town;
    }

    public void setTown(@NonNull String town) {
        this.town = town;
    }

    @NonNull
    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(@NonNull String streetNo) {
        this.streetNo = streetNo;
    }

    @NonNull
    public String getDepNo() {
        return depNo;
    }

    public void setDepNo(@NonNull String depNo) {
        this.depNo = depNo;
    }

    @NonNull
    public String getMoreDetails() {
        return moreDetails;
    }

    public void setMoreDetails(@NonNull String moreDetails) {
        this.moreDetails = moreDetails;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package com.example.GradProJM.Model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int userid;
    @Column(nullable = false)
    private String userName,userPNum,userEmail,userPass,userAddID;
    @Column(nullable = false)

    private int status;


    @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL)
    private ShopOwner shopowner;



    @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL)
    private Customer customer;


    public User() {
    }

    public User(int userid, String userName, String userPNum, String userEmail, String userPass, String userAddID,int status) {
        this.userid = userid;
        this.userName = userName;
        this.userPNum = userPNum;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userAddID = userAddID;
        this.status=status;
    }
    public User(String userName, String userPNum, String userEmail, String userPass, String userAddID,int status , Customer customer) {
        this.userName = userName;
        this.userPNum = userPNum;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userAddID = userAddID;
        this.status=status;
        this.customer = customer ;
    }


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPNum() {
        return userPNum;
    }

    public void setUserPNum(String userPNum) {
        this.userPNum = userPNum;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserAddID() {
        return userAddID;
    }

    public void setUserAddID(String userAddID) {
        this.userAddID = userAddID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ShopOwner getShopowner() {
        return shopowner;
    }

    public void setShopowner(ShopOwner shopowner) {
        this.shopowner = shopowner;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", userName='" + userName + '\'' +
                ", userPNum='" + userPNum + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPass='" + userPass + '\'' +
                ", userAddID='" + userAddID + '\'' +
                ", status=" + status +
                '}';
    }
}
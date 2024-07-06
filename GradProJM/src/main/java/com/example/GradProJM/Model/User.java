package com.example.GradProJM.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name="user")
@JsonSerialize(using = UserSerializer.class)
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;
    @Column(nullable = false)
    private String userName,userPNum,userEmail,userPass;
    private String joinDate;
    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private boolean verified;
    @NonNull
    private int addCount;


    @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL)
    private ShopOwner shopowner;



    @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL)
    private Customer customer;

    @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL)
    private Admin admin;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//    @JsonManagedReference
    private List<Address> address;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<PaymentMethods> paymentMethods;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Order> orders;

    public User() {
        this.verified=false;
    }

    public User(int userid, String userName, String userPNum, String userEmail, String userPass,int status,String joinDate, boolean verifie) {
        this.userid = userid;
        this.userName = userName;
        this.userPNum = userPNum;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.status=status;
        this.joinDate=String.valueOf(LocalDate.now());
        this.verified=false;
    }
    public User(String userName, String userPNum, String userEmail, String userPass, int status , Customer customer,String joinDate, boolean verifie) {
        this.userName = userName;
        this.userPNum = userPNum;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.status=status;
        this.customer = customer ;
        this.joinDate=String.valueOf(LocalDate.now());
        this.verified=false;
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


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
//    @JsonIgnore
    public ShopOwner getShopowner() {
        return shopowner;
    }
//    @JsonIgnore
    public void setShopowner(ShopOwner shopowner) {
        this.shopowner = shopowner;
    }
//    @JsonIgnore
    public Customer getCustomer() {
        return customer;
    }
//    @JsonIgnore
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

//    @JsonIgnore
    public List<Address> getAddress() {
        return address;
    }
//    @JsonIgnore
    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<PaymentMethods> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethods> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getAddCount() {
        return addCount;
    }

    public void setAddCount(int addCount) {
        this.addCount = addCount;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", userName='" + userName + '\'' +
                ", userPNum='" + userPNum + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPass='" + userPass + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return userPass;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


//    public static void deleteUnverifiedUsersAfterTimeout(EntityManager entityManager) {
//        Duration timeoutDuration = Duration.ofMinutes(1);
//        LocalDateTime thresholdDateTime = LocalDateTime.now().minus(timeoutDuration);
//
//
//        List<User> unverifiedUsers = entityManager.createQuery(
//                        "SELECT u FROM User u WHERE u.verified = false AND u.joinDate < :thresholdDate",
//                        User.class)
//                .setParameter("thresholdDate", thresholdDateTime)
//                .getResultList();
//        for (User user : unverifiedUsers) {
//            entityManager.getTransaction().begin();
//            entityManager.remove(user);
//            entityManager.getTransaction().commit();
//            System.out.println("Deleted unverified user: " + user.getUserName());
//        }
//    }
//
//    public static void scheduleUnverifiedUserDeletion(EntityManager entityManager) {
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        scheduler.scheduleAtFixedRate(() -> deleteUnverifiedUsersAfterTimeout(entityManager),
//                0, 1, TimeUnit.MINUTES);
//    }
}
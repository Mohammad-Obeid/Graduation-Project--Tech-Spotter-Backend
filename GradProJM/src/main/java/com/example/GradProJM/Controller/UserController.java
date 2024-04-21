package com.example.GradProJM.Controller;


import com.example.GradProJM.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.GradProJM.Services.*;

@RestController
@RequestMapping(path="user")
public class UserController {
    private final userService userService;


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                       User Work// customer, ShopIwner, Registration, Login, Verification, Deletion, update.
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static String code = "";
    private static User user1 = new User();
    private static LocalDateTime loctime;
    @Autowired
    public UserController(userService userService) {
        this.userService = userService;
    }
    @GetMapping("users")
    public ResponseEntity<List> std() {
        Optional<List> users= Optional.ofNullable(userService.getUsers());
        return users.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));

    }
    @PostMapping("addnewuser")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        System.out.println("User Object : " + user.toString());
        user1 = user;
        Optional<User> us= Optional.ofNullable(userService.SendEmailVerification(user));
        loctime = LocalDateTime.now().plusMinutes(10);
        return us.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FOUND)
                        .body(null));    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int userid) {
        Optional<User> user = Optional.ofNullable(userService.getUserbyId(userid));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

//    @GetMapping("/getcode")
//    public String getCode() {
//        code = userService.getCode();
//        return code;
//    }
    @PostMapping("/verifycode")
    public ResponseEntity<String> verifyCode(@RequestBody String code1) {
//        getCode();
        code = userService.getCode();
        LocalDateTime timeNow = LocalDateTime.now();
        if (loctime.isAfter(timeNow)) {
            if (code1.replace("\"", "").equals(code)) {
                List<Address> address = user1.getAddress();
                if (user1.getStatus() == 0) {
                    Customer customer = user1.getCustomer();
                    customer.setUser(user1);
                } else if (user1.getStatus() == 1) {
                    ShopOwner shop = user1.getShopowner();
                    shop.setUser(user1);
                    System.out.println(shop.toString());
                }
                for(int i=0;i<address.size();i++){
                    address.get(i).setUser(user1);
                }
                userService.addUser(user1);
                return ResponseEntity.ok().build();
            } else {
                System.out.println("lalallala");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Code Doesn't Match");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Verification Code has been Expired...");
    }


    @DeleteMapping("deleteuser/{userID}")
    public ResponseEntity<User> deleteUser(@PathVariable("userID") int userID){
        Optional<User> user = userService.DeleteUser(userID);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                          Addresses Work, insertion, Deletion, update, get
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("addNewAddress/{userID}")
    public ResponseEntity<User> addNewAddress(@PathVariable("userID") int userID, @RequestBody Address address){
        Optional<User> user= Optional.ofNullable(userService.AddNewAddress(userID, address));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @DeleteMapping("deleteAddress/{userID}/{addressName}")
    public ResponseEntity<User> deleteAddress(@PathVariable("userID") int userID, @PathVariable("addressName") String addName){
        Optional<User> user=Optional.ofNullable(userService.RemoveAddress(userID,addName));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
    @PatchMapping("updateAddress/{userID}/{addID}")
    public ResponseEntity<User> updateAddress(@PathVariable("userID") int userID, @PathVariable("addID") int addID, @RequestBody Address add){
        Optional<User> user= Optional.ofNullable(userService.updateAddress(userID, addID,add));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                  Customer and ShopOwner Information Update
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PatchMapping("updateCustomer/{userID}")
    public ResponseEntity<User> updateCustomerInformation(@PathVariable("userID") int userID, @RequestBody User userr){
        Optional<User> user= Optional.ofNullable(userService.updateCustomerInformation(userID, userr));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PatchMapping("updateShopOwner/{userID}")
    public ResponseEntity<User> updateShopOwnerInformation(@PathVariable("userID") int userID, @RequestBody User userr){
        Optional<User> user= Optional.ofNullable(userService.updateShopOwnerInformation(userID, userr));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                     Payment Methods Work, addition, deletion, update, get ...
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    @PostMapping("AddPaymentMethod/{userID}")
    public ResponseEntity<User> AddNewPaymentMethod(@PathVariable("userID") int userID, @RequestBody PaymentMethods paymentMethod){
        Optional<User> user= Optional.ofNullable(userService.AddNewPaymentMethod(userID,paymentMethod));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @PatchMapping("UpdatePayment/{userID}/{paymentName}")
    public ResponseEntity<User> UpdatePaymentByPayNameForAUser(@PathVariable("userID") int userID, @PathVariable("paymentName") String paymentName,@RequestBody PaymentMethods paymentMethod){
        Optional<User> user= Optional.ofNullable(userService.UpdatePaymentByPayNameForAUser(userID,paymentName,paymentMethod));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PatchMapping("UpdatePaymentbyid/{userID}/{paymentID}")
    public ResponseEntity<User> UpdatePaymentByPayIDForAUser(@PathVariable("userID") int userID, @PathVariable("paymentID") int paymentID,@RequestBody PaymentMethods paymentMethod){
        Optional<User> user= Optional.ofNullable(userService.UpdatePaymentByPayIDForAUser(userID,paymentID,paymentMethod));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @DeleteMapping("deletePaymentMethod/{userID}/{paymentID}")
    public ResponseEntity<User> DeletePaymentMethod(@PathVariable("userID") int userID,@PathVariable("paymentID") int paymentID){
        Optional<User> user= Optional.ofNullable(userService.DeletePaymentMethod(userID,paymentID));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
}

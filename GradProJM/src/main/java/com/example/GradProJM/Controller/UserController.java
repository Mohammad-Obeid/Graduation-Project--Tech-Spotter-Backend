package com.example.GradProJM.Controller;


import com.example.GradProJM.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.example.GradProJM.Services.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path="user")
public class UserController {
    private final userService userService;


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                       User Work// customer, ShopIwner, Registration, Login, Verification, Deletion, update.
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
    public ResponseEntity<User> addNewUser(@RequestBody User user){
        Optional<User> us= Optional.ofNullable(userService.SendEmailVerification(user));
        return us.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FOUND)
                        .body(null));
    }
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
//    @PostMapping("/verifycode")
//    public ResponseEntity<String> verifyCode(@RequestBody codeVerificationCheck check) {
//        String code = userService.getCode(check.getUserEmail());
//        if(!code.equals("")) {
//            LocalDateTime timeNow = LocalDateTime.now();
//            if (loctime.isAfter(timeNow)) {
//                if (check.getVerificationCode().replace("\"", "").equals(code)) {
//                    List<Address> address = user1.getAddress();
//                    if (user1.getStatus() == 0) {
//                        Customer customer = user1.getCustomer();
//                        customer.setShoppingCart(userService.GetShoppingCart());
//                        customer.setUser(user1);
//                    } else if (user1.getStatus() == 1) {
//                        ShopOwner shop = user1.getShopowner();
//                        shop.setUser(user1);
//                        System.out.println(shop.toString());
//                    }
//                    for (int i = 0; i < address.size(); i++) {
//                        address.get(i).setUser(user1);
//                    }
//                    userService.addUser(user1);
//                    return ResponseEntity.ok().build();
//                } else {
//                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Code Doesn't Match");
//                }
//            }
//
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Verification Code has been Expired...");
//        }
//        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User wasn't Found!");
//    }


    @PostMapping("/verifycode")
    public ResponseEntity<String> verifyCode(@RequestBody codeVerificationCheck check) {
        User user = userService.getUserByEmail(check.getUserEmail());
        if(user!=null) {
            String code = userService.getCode(check.getUserEmail());
            LocalDateTime lc = LocalDateTime.parse(userService.getTime(check.getUserEmail()));
            LocalDateTime timeNow = LocalDateTime.now();
            if (timeNow.isBefore(lc.plusMinutes(10))) {
                BCryptPasswordEncoder bCryptCodeEncoder = new BCryptPasswordEncoder();
                if(bCryptCodeEncoder.matches(check.getVerificationCode(), code))
                {
                    if (user.getStatus() == 0) {
                        Customer customer = user.getCustomer();
                        customer.setShoppingCart(userService.GetShoppingCart());
                        customer.setWishlist(userService.GetWishList());
                        customer.setUser(user);
                    } else if (user.getStatus() == 1) {
                        ShopOwner shop = user.getShopowner();
                        shop.setUser(user);
                        System.out.println(shop.toString());
                    }
                    userService.addUser(user);
                    return ResponseEntity.ok().build();
                }
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Code Doesn't Match");

            }
            userService.DeleteUser(user.getUserid());
            userService.removetempCheck(user.getUserEmail());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Verification Code has been Expired...");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User Wasn't Found!");
    }
    @GetMapping("loginUser")
    public ResponseEntity<String> Login(@RequestBody LoginRequest loginreq){
        Optional<String> LoginUser= Optional.ofNullable(userService.Login(loginreq));
        return LoginUser.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(""));
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
                .orElse(ResponseEntity.status(HttpStatus.FOUND)
                        .body(null));
    }

    @DeleteMapping("deleteAddress/{userID}")
    public ResponseEntity<User> deleteAddress(@PathVariable("userID") int userID, @RequestBody Address address){
        Optional<User> user=Optional.ofNullable(userService.RemoveAddress(userID,address));
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

    @PostMapping("getUserByEmail")
    public ResponseEntity<User> getUserByEmail(@RequestBody LoginRequest login){
        Optional<User> user= Optional.ofNullable(userService.getUserByEmaill(login));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PatchMapping("changePassword/{userID}")
    public ResponseEntity<User> changePassword(@PathVariable("userID") int userID,
            @RequestBody PasswordChangeChecking us){
        Optional<User> user= Optional.ofNullable(userService.changePassword(us,userID));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
}

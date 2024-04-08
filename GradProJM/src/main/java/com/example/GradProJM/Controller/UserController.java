package com.example.GradProJM.Controller;


import com.example.GradProJM.Model.Address;
import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.ShopOwner;
import com.example.GradProJM.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.example.GradProJM.Services.*;

@RestController
@RequestMapping(path="user")
public class UserController {
    private final userService userService;

    private static String code = "";
    private static User user1 = new User();
    private static LocalDateTime loctime;
    @Autowired
    public UserController(userService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<List> std() {
        Optional<List> users= Optional.ofNullable(userService.getUsers());
        return users.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));

    }
    @PostMapping("addnewuser")
    public ResponseEntity<Void> addNewUser(@RequestBody User user) {
        System.out.println("User Object : " + user.toString());
        user1 = user;
        userService.SendEmailVerification(user);
        loctime = LocalDateTime.now().plusMinutes(10);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int userid) {
        Optional<User> user = Optional.ofNullable(userService.getUserbyId(userid));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @GetMapping("/getcode")
    public String getCode() {
        code = userService.getCode();
        return code;
    }
    @PostMapping("/verifycode")
    public ResponseEntity<Void> verifyCode(@RequestBody String code1) {
        getCode();
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
                throw new IllegalStateException("Code Doesn't Match");
//                return ResponseEntity.badRequest().build();
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//            return ResponseEntity.notFound().build();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//          Search which response is the suitable for this case.......
            }
        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        throw new IllegalStateException("Verification Code has been Expired...");

    }


    @DeleteMapping("deleteuser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int userID){
        if(userService.getUserbyId(userID)!=null){
        userService.DeleteUser(userID);
        return ResponseEntity.ok().build();
        }
        else
//            return ResponseEntity.notFound().build();
            throw new IllegalStateException("User with ID: "+userID+" Wasn't Found");
    }
    @PostMapping("addNewAddress/{userID}")
    public ResponseEntity<User> addNewAddress(@PathVariable("userID") int userID, @RequestBody Address address){
        Optional<User> user= Optional.ofNullable(userService.AddNewUser(userID, address));
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

}

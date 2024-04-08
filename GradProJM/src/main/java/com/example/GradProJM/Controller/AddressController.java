package com.example.GradProJM.Controller;


import com.example.GradProJM.Model.Address;
import com.example.GradProJM.Services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("address")
@RestController
public class AddressController {
    private final AddressService addservice;
    @Autowired
    public AddressController(AddressService addservice) {
        this.addservice = addservice;
    }
    @GetMapping("getaddresses/{id}")
    public List<Address> showAddForaUser(@PathVariable("id") int userid){
        return addservice.getAddresses(userid);
    }

//    @PostMapping("addnewadd/{id}")
//    public ResponseEntity<Void> addNewAddress(@PathVariable("id") int userID, @RequestBody Address address){
//        addservice.AddnewAddress(userID, address);
//        return ResponseEntity.ok().build();
//    }

//    @DeleteMapping("deleteadd/{userid}/{addid}")
//    public ResponseEntity<Void> deleteAddress(@PathVariable("userid") int userID, @PathVariable("addid") int addID){
//        addservice.deleteAddress(userID, addID);
//        return ResponseEntity.ok().build();
//    }


}

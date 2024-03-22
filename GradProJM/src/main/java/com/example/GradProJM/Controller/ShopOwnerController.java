package com.example.GradProJM.Controller;

import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.ShopOwner;
import com.example.GradProJM.Services.shopOwnerService;
import com.example.GradProJM.Services.shopOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path="ShopOwner")
@RestController
public class ShopOwnerController {

    private final shopOwnerService shopOwnerService;

    @Autowired
    public ShopOwnerController(shopOwnerService shopownerService) {
        this.shopOwnerService = shopownerService;
    }


    @GetMapping
    public List<ShopOwner> allShopOwners(){
        return shopOwnerService.getCustomers();
    }

    @PostMapping
    public void addNewShop(@RequestBody ShopOwner shopOwner){
        shopOwnerService.addNewShopOwner(shopOwner);
    }

    @GetMapping("/{id}")
    public ShopOwner getShopbyID(@PathVariable("id") int ShopID){
        return shopOwnerService.getShopOwnerbyId(ShopID);
    }


}

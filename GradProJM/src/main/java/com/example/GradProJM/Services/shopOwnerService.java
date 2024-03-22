package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.ShopOwner;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.ShopOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class shopOwnerService {
    private static ShopOwnerRepository shopOwnerRepo;

    @Autowired
    public shopOwnerService(ShopOwnerRepository shopOwnerRepo) {
        this.shopOwnerRepo = shopOwnerRepo;
    }

    public List<ShopOwner> getCustomers(){
        return  shopOwnerRepo.findAll();
    }

    public static void addNewShopOwner(ShopOwner shopOwner) {
//        Optional<Customer> findBycustid=shopOwnerRepo.findCustomerByCustID(customer.getCustID());
//        if(findBycustid.isPresent()){
//            throw new IllegalStateException("Email Taken from Customer ");
//        }
//        else {
            shopOwnerRepo.save(shopOwner);
//        }
    }

    public ShopOwner getShopOwnerbyId(int shopOwnerID) {
        Optional<ShopOwner> findByShopid=shopOwnerRepo.findById(shopOwnerID);
        return findByShopid.orElse(null);
    }

}

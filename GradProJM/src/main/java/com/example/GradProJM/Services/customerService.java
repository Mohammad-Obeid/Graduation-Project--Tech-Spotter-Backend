package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.Shop_Products;
import com.example.GradProJM.Model.User;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.ProductRepository;
import com.example.GradProJM.Repos.ShopOwnerRepository;
import com.example.GradProJM.Repos.productShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class customerService {
    private static CustomerRepository custRepo;

    private final productShopRepository prdshpRepo;
    private final ProductRepository prdRepo;
    private final ShopOwnerRepository shpRepo;
    public customerService(productShopRepository prdshpRepo,
                           ProductRepository prdRepo,
                           ShopOwnerRepository shpRepo,
                           CustomerRepository custRepo) {
        this.prdshpRepo=prdshpRepo;
        this.prdRepo=prdRepo;
        this.shpRepo=shpRepo;
        this.custRepo = custRepo;
    }

    public List<Customer> getCustomers(){
        return  custRepo.findAll();
    }

    public static void addNewCustomer(Customer customer) {
        Optional<Customer> findBycustid=custRepo.findCustomerByCustID(customer.getCustID());
        if(findBycustid.isPresent()){
            throw new IllegalStateException("Email Taken from Customer ");
        }
        else {
            custRepo.save(customer);
        }
    }

    public Customer getCustomerbyId(int custID) {
        Optional<Customer> findBycustid=custRepo.findCustomerByCustID(custID);
        return findBycustid.orElse(null);
    }



    public List<Shop_Products> Search(String prodName) {
        Optional<List<Shop_Products>> products = prdshpRepo.findShop_ProductsByProductProductNameStartingWith(prodName);
        return products.get();
    }
    public List<Shop_Products> SortASC(String prodName, String field) {
        Optional<List<Shop_Products>> products = prdshpRepo.findShop_ProductsByProductProductNameStartingWith(prodName,Sort.by(Sort.Direction.ASC, field));
        return products.get();
    }

    public List<Shop_Products> SortDESC(String prodName, String field) {
        Optional<List<Shop_Products>> products = prdshpRepo.findShop_ProductsByProductProductNameStartingWith(prodName,Sort.by(Sort.Direction.DESC, field));
        return products.get();
    }




}
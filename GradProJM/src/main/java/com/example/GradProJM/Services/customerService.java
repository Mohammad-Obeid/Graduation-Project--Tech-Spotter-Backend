package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.Shop_Products;
import com.example.GradProJM.Model.User;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.ProductRepository;
import com.example.GradProJM.Repos.ShopOwnerRepository;
import com.example.GradProJM.Repos.productShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    //todo: change page Size to 10;

    public List<Shop_Products> Search(String prodName, int pageNum) {
        Optional<List<Shop_Products>> products = prdshpRepo.findShop_ProductsByProductProductNameStartingWith(prodName, PageRequest.of(pageNum,8));
        return products.get();
    }

    public Page<Shop_Products> SortASC(String prefix, int page, String sortField) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.ASC, sortField));
        return prdshpRepo.findShop_ProductsByProductProductNameStartingWith(prefix, pageable);
    }

    public Page<Shop_Products> SortDESC(String prefix, int page, String sortField) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, sortField));
        return prdshpRepo.findShop_ProductsByProductProductNameStartingWith(prefix, pageable);
    }





}
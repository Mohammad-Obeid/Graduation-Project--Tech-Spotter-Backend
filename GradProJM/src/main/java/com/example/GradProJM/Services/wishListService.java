package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.Shop_Products;
import com.example.GradProJM.Model.wishList;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.productShopRepository;
import com.example.GradProJM.Repos.wishListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class wishListService {
    private final wishListRepository wishRepo;

    private final productShopRepository prdshpRepo;
    private final CustomerRepository custRepo;

    public wishListService(wishListRepository wishRepo,
                           productShopRepository prdshpRepo,
                           CustomerRepository custRepo) {
        this.wishRepo = wishRepo;
        this.prdshpRepo = prdshpRepo;
        this.custRepo=custRepo;
    }

    public List getWishListItemsForAUser(int custID) {
        Optional<Customer> cust = custRepo.findCustomerByCustID(custID);
        if(cust.isPresent()){
            Optional<wishList> wishlst = wishRepo.findById(cust.get().getWishlist().getWishListID());
            List<Shop_Products> products = wishlst.get().getProducts();
            return products;
        }
        return null;
    }

    public wishList addProductToWishList(int custID, int prodID) {
        Optional<Customer> cust = custRepo.findCustomerByCustID(custID);
        if(cust.isPresent()){
            Optional<wishList> wishlst = wishRepo.findById(cust.get().getWishlist().getWishListID());
            List<Shop_Products> products = wishlst.get().getProducts();
            Optional<Shop_Products> prod = prdshpRepo.findById(prodID);
            if(!products.contains(prod.get())){
            products.add(prod.get());
            wishlst.get().setProducts(products);
            wishRepo.save(wishlst.get());
            return wishlst.get();}
            return null;
        }
        return null;
    }

    public wishList removeProductFromWishList(int custID, int prodID) {
        Optional<Customer> cust = custRepo.findCustomerByCustID(custID);
        if(cust.isPresent()){
            Optional<wishList> wishlst = wishRepo.findById(cust.get().getWishlist().getWishListID());
            List<Shop_Products> products = wishlst.get().getProducts();
            Optional<Shop_Products> prod = prdshpRepo.findById(prodID);
            if(products.contains(prod.get())){
                products.remove(prod.get());
                wishlst.get().setProducts(products);
                wishRepo.save(wishlst.get());
                return wishlst.get();}
            return null;
        }
        return null;
    }
}

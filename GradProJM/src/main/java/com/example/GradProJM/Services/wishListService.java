package com.example.GradProJM.Services;

import com.example.GradProJM.Model.Customer;
import com.example.GradProJM.Model.Shop_Products;
import com.example.GradProJM.Model.User;
import com.example.GradProJM.Model.wishList;
import com.example.GradProJM.Repos.CustomerRepository;
import com.example.GradProJM.Repos.productShopRepository;
import com.example.GradProJM.Repos.userRepository;
import com.example.GradProJM.Repos.wishListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class wishListService {
    private final wishListRepository wishRepo;

    private final productShopRepository prdshpRepo;
    private final CustomerRepository custRepo;
    private final userRepository userRepo;


    public wishListService(wishListRepository wishRepo,
                           productShopRepository prdshpRepo,
                           CustomerRepository custRepo, userRepository userRepo) {
        this.wishRepo = wishRepo;
        this.prdshpRepo = prdshpRepo;
        this.custRepo=custRepo;
        this.userRepo = userRepo;
    }

    public List getWishListItemsForAUser(int userID) {
        Optional<User> user = userRepo.findById(userID);
        if(user.isPresent() && user.get().getStatus()==0){
            Optional<Customer> cust = custRepo.findById(user.get().getCustomer().getCustID());
            Optional<wishList> wishlst = wishRepo.findById(cust.get().getWishlist().getWishListID());
            List<Shop_Products> products = wishlst.get().getProducts();
            return products;
        }
        return null;
    }
    //todo: make paging

    public wishList addProductToWishList(int userID, int prodID) {
        System.out.println("///////////////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////////////");
        System.out.println("///////////////////////////////////////////////////////////////");
        Optional<User> user = userRepo.findById(userID);
        if(user.isPresent() && user.get().getStatus()==0){
            Optional<Customer> cust = custRepo.findById(user.get().getCustomer().getCustID());
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

    public wishList removeProductFromWishList(int userID, int prodID) {

        Optional<User> user = userRepo.findById(userID);
        if(user.isPresent() && user.get().getStatus()==0){
            Optional<Customer> cust = custRepo.findById(user.get().getCustomer().getCustID());
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

    public wishList addProductToWishList(int userID, String shopName, String prodBarcode) {
        Optional<Shop_Products> prod = prdshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcodeAndDeletedFalse(shopName,prodBarcode);
        return addProductToWishList(userID,prod.get().getId());
    }
}

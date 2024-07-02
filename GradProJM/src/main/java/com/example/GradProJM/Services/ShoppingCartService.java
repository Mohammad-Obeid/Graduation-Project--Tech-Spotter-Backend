package com.example.GradProJM.Services;


import com.example.GradProJM.Model.*;
import com.example.GradProJM.Repos.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shpCrtRepo;
    private final CustomerRepository custRepo;
    private final ProductRepository prodRepo;
    private final productShopRepository prodshpRepo;
    private final userRepository userRepo;



    public ShoppingCartService(ShoppingCartRepository shpCrtRepo,
                               CustomerRepository custRepo,
                               ProductRepository prodRepo,
                               productShopRepository prodshpRepo, userRepository userRepo) {
        this.shpCrtRepo = shpCrtRepo;
        this.custRepo=custRepo;
        this.prodRepo=prodRepo;
        this.prodshpRepo=prodshpRepo;
        this.userRepo = userRepo;
    }


    public List getCartProducts(int custID) {
        Optional<User> user=userRepo.findById(custID);
        if(user.isPresent()){
            Optional<Customer> cust = custRepo.findById(user.get().getUserid());
            ShoppingCart shpcrt=cust.get().getShoppingCart();
            return shpcrt.getCartItems();
        }
        return null;
    }


    //todo: check paging
    public ShoppingCart AddProductToCart(int custID, int prodID) {
        Optional<User> user=userRepo.findById(custID);
        if(user.isPresent()){
            Optional<Customer> cust = custRepo.findById(user.get().getUserid());
            ShoppingCart shpcrt=cust.get().getShoppingCart();
            Optional<Shop_Products> prod=prodshpRepo.findById(prodID);
            if(prod.isPresent()){
                double totprice= shpcrt.getTotalPrice();
                totprice+=prod.get().getProductPrice();
                List<CartItems> products=shpcrt.getCartItems();
                int flag=0;
                for(int i=0;i<products.size();i++){
                    if(products.get(i).getProduct().getId()==prodID)flag=1;
                }
                if(flag==0){
                    CartItems item= new CartItems();
                    item.setProduct(prod.get());
                    item.setQuantity(1);
                    item.setProductTotalPrice(prod.get().getProductPrice());
                    products.add(item);
                    shpcrt.setCartItems(products);
                    shpcrt.setTotalPrice(totprice);
                    shpCrtRepo.save(shpcrt);
                    return shpcrt;
                }
                return null;
            }
            return null;
        }
        return null;
    }


    public ShoppingCart AddProductToCart(int custID, String shopName, String prodBarcode) {
        Optional<Shop_Products> product = prodshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcode(shopName, prodBarcode);
        return AddProductToCart(custID, product.get().getId());
    }


    public ShoppingCart DeleteProductFromCart(int custID, int prodID) {
        Optional<User> user=userRepo.findById(custID);
        if(user.isPresent()) {
            Optional<Customer> customer = custRepo.findById(user.get().getUserid());
            ShoppingCart shpcrt = customer.get().getShoppingCart();
            Optional<Shop_Products> prod=prodshpRepo.findById(prodID);
            if (prod.isPresent()) {
                double totPrice=0;
                totPrice=shpcrt.getTotalPrice();
                totPrice-=prod.get().getProductPrice();
                List<CartItems> products=shpcrt.getCartItems();
                products.remove(prod.get());
                shpcrt.setTotalPrice(totPrice);

                shpcrt.setCartItems(products);
                shpCrtRepo.save(shpcrt);
                return shpcrt;

            }

            return null;
        }
        return null;
    }
    public ShoppingCart DeleteProductFromCart(int custID, String shopName, String prodBarcode) {
        Optional<Shop_Products> product = prodshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcode(shopName, prodBarcode);
        return DeleteProductFromCart(custID, product.get().getId());
    }
}

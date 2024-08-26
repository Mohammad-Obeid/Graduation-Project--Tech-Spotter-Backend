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

    private final CartItemRepository cartItmRepo;



    public ShoppingCartService(ShoppingCartRepository shpCrtRepo,
                               CustomerRepository custRepo,
                               ProductRepository prodRepo,
                               productShopRepository prodshpRepo, userRepository userRepo, CartItemRepository cartItmRepo) {
        this.shpCrtRepo = shpCrtRepo;
        this.custRepo=custRepo;
        this.prodRepo=prodRepo;
        this.prodshpRepo=prodshpRepo;
        this.userRepo = userRepo;
        this.cartItmRepo = cartItmRepo;
    }


    public List getCartProducts(int custID) {
        Optional<User> user=userRepo.findById(custID);
        if(user.isPresent() && user.get().getStatus()==0){
            Optional<Customer> cust = custRepo.findById(user.get().getCustomer().getCustID());
            ShoppingCart shpcrt=cust.get().getShoppingCart();
            return shpcrt.getCartItems();
        }
        return null;
    }


    //todo: check paging
    public ShoppingCart AddProductToCart(int userID, int prodID) {
        Optional<User> user=userRepo.findById(userID);
        if(user.isPresent() && user.get().getStatus()==0){
            System.out.println("/////////////////////////////////////////////////////////");
            System.out.println("/////////////////////////////////////////////////////////");
            System.out.println("/////////////////////////////////////////////////////////");
            System.out.println("/////////////////////////////////////////////////////////");
            System.out.println("/////////////////////////////////////////////////////////");
            System.out.println("/////////////////////////////////////////////////////////");
            System.out.println("/////////////////////////////////////////////////////////");
            System.out.println("/////////////////////////////////////////////////////////");
            Optional<Customer> cust = custRepo.findById(user.get().getCustomer().getCustID());
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
                    item.setCartID(shpcrt.getCartID());
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
        Optional<Shop_Products> product = prodshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcodeAndDeletedFalse(shopName, prodBarcode);
        return AddProductToCart(custID, product.get().getId());
    }


    public ShoppingCart DeleteProductFromCart(int custID, int prodID) {
        Optional<User> user=userRepo.findById(custID);
        if(user.isPresent() && user.get().getStatus()==0) {
            Optional<Customer> customer = custRepo.findById(user.get().getCustomer().getCustID());
            Optional<ShoppingCart> shpcrt = shpCrtRepo.findById(customer.get().getShoppingCart().getCartID());
            Optional<Shop_Products> prod=prodshpRepo.findById(prodID);
            if (prod.isPresent()) {
                Optional<CartItems> ct = cartItmRepo.findByProductIdAndCartID(prodID,shpcrt.get().getCartID());
                double totPrice=0;
                totPrice=shpcrt.get().getTotalPrice();
                totPrice-=prod.get().getProductPrice()*ct.get().getQuantity();
                List<CartItems> products=shpcrt.get().getCartItems();
                products.remove(ct.get());
                shpcrt.get().setTotalPrice(totPrice);
                ct.get().setProduct(null);
                cartItmRepo.delete(ct.get());
                System.out.println(".////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
                System.out.println(prodID+",,"+shpcrt.get().getCartID());
                System.out.println(".////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
                System.out.println(ct.get().toString());
                shpcrt.get().setCartItems(products);
                shpCrtRepo.save(shpcrt.get());
                return shpcrt.get();

            }

            return null;
        }
        return null;
    }
    public ShoppingCart DeleteProductFromCart(int custID, String shopName, String prodBarcode) {
        Optional<Shop_Products> product = prodshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcodeAndDeletedFalse(shopName, prodBarcode);
        return DeleteProductFromCart(custID, product.get().getId());
    }
}

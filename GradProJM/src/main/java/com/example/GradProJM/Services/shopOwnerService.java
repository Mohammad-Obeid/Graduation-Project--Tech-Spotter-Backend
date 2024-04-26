package com.example.GradProJM.Services;

import com.example.GradProJM.Model.ShopOwner;
import com.example.GradProJM.Model.product;
import com.example.GradProJM.Repos.ProductRepository;
import com.example.GradProJM.Repos.ShopOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class shopOwnerService {
    private static ShopOwnerRepository shopOwnerRepo;
    private static ProductRepository prodRepo;

    @Autowired
    public shopOwnerService(ShopOwnerRepository shopOwnerRepo,ProductRepository prodRepo) {
        this.shopOwnerRepo = shopOwnerRepo;
        this.prodRepo=prodRepo;
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

//    public ShopOwner AddNewProduct(int shopID, product prod) {
//        System.out.println("MOE");
//        Optional<ShopOwner> shop=shopOwnerRepo.findById(shopID);
//        if(shop.isPresent()){
//            //todo: make sure product doesn't exist
//            List<product> products=shop.get().getProducts();
//            prod.setShopOwner(shop.get());
//            products.add(prod);
//            shop.get().setProducts(products);
//            shopOwnerRepo.save(shop.get());
//            System.out.println("slsllalala");
//            return shop.get();
//        }
//        else
//            return null;
//    }

//    public ShopOwner ModifyProductForShopOwner(int shopOwnerID, int productID, product prodd) {
//        Optional <ShopOwner> shop=shopOwnerRepo.findById(shopOwnerID);
//        if(shop.isPresent()){
//            Optional<product> prod=prodRepo.findById(productID);
//            if(prod.isPresent()){
//                prodd.setProductId(prod.get().getProductId());
//                prodd.setShopOwner(shop.get());
////                List<product> products =shop.get().getProducts();
////                produc
//                prodRepo.save(prodd);
//                return shop.get();
//            }
//            return null;
//        }
//        return null;
//    }

    public ShopOwner DeleteProductForShopOwner(int shopID, int productID) {
        Optional<ShopOwner> shop=shopOwnerRepo.findById(shopID);
        if(shop.isPresent()){
            Optional<product> prod =prodRepo.findById(productID);
            if(prod.isPresent()){
//                prodRepo.deleteById(productID);
//                List<product>products=shop.get().getProducts();
////                products.remove(prod.get());
//                shop.get().setProducts(products);
//                shopOwnerRepo.save(shop.get());



                product productToRemove = prod.get();
//                shop.get().getProducts().remove(productToRemove);
                shopOwnerRepo.save(shop.get());
                prodRepo.deleteById(productID);
                return shop.get();



//                return shop.get();

            }
            else return null;

        }
        return null;
    }
}

package com.example.GradProJM.Services;

import com.example.GradProJM.Model.ShopOwner;
import com.example.GradProJM.Model.Shop_Products;
import com.example.GradProJM.Model.product;
import com.example.GradProJM.Repos.ProductRepository;
import com.example.GradProJM.Repos.ShopOwnerRepository;
import com.example.GradProJM.Repos.productShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductShopService {
    private static productShopRepository prdshpRepo;
    private static ProductRepository prdRepo;
    private static ShopOwnerRepository shpRepo;

    @Autowired
    public ProductShopService(productShopRepository prdshpRepo,
                              ProductRepository prdRepo,
                              ShopOwnerRepository shpRepo) {
        this.prdshpRepo=prdshpRepo;
        this.prdRepo=prdRepo;
        this.shpRepo=shpRepo;
    }

    public Boolean AddAnExistingProducttoaShop(Shop_Products shopProducts) {
        Optional<product> product=prdRepo.findById(shopProducts.getProduct().getProductId());
        if(product.isPresent()){
            Optional<ShopOwner> shop=shpRepo.findById(shopProducts.getShop().getShopID());
            if(shop.isPresent()){
                prdshpRepo.save(shopProducts);
                return true;
            }
            return null;
        }
        return null;
    }

    public Boolean AddNewProducttoaShop(Shop_Products shopProducts) {
        Optional<product> checkprod=prdRepo.findByproductBarcode(shopProducts.getProduct().getProductBarcode());
        int flag=0;
        List<Shop_Products> chk=prdshpRepo.findAll();
        for(Shop_Products sh: chk){
            if(sh.getProduct().getProductBarcode().equals(shopProducts.getProduct().getProductBarcode()) &&
                    sh.getShop().getShopID()==shopProducts.getShop().getShopID()){
                flag=1;
            }
        }
        if(flag==0) {
            if (checkprod.isPresent()) {
                return null;
            } else {
                product prod = shopProducts.getProduct();
                prdRepo.save(prod);
                prdshpRepo.save(shopProducts);
                return true;
            }
        }
        return null;
    }

    public Boolean AddAnExistingProductByBarcodetoaShop(Shop_Products shopProducts) {
        Optional<product> product=prdRepo.findByproductBarcode(shopProducts.getProduct().getProductBarcode());
        int flag=0;
        List<Shop_Products> chk=prdshpRepo.findAll();
        for(Shop_Products sh: chk){
            if(sh.getProduct().getProductBarcode().equals(shopProducts.getProduct().getProductBarcode()) &&
            sh.getShop().getShopID()==shopProducts.getShop().getShopID()){
                flag=1;
            }
        }
        if(flag==0) {
        if(product.isPresent()){
            Optional<ShopOwner> shop=shpRepo.findById(shopProducts.getShop().getShopID());
            if(shop.isPresent()){
                System.out.println(product.get());
                Shop_Products shpprdct= new Shop_Products(shopProducts.getShop(),product.get(),shopProducts.getQuantity());
                prdshpRepo.save(shpprdct);
                return true;
            }
            return null;
        }

        }
        return null;
    }

    public List getAllProductsForAShop(int shopID) {
        Optional<List> products = prdshpRepo.findShop_ProductsByShop_ShopID(shopID);
        return products.orElse(null);
    }

    public Boolean DeleteAProductFromAShop(int shopID, int prodID) {
        Optional<ShopOwner> shop=shpRepo.findById(shopID);
        List<Shop_Products> shopProducts = prdshpRepo.findAll();
        if(shop.isPresent()){
            Optional<product> prod=prdRepo.findById(prodID);
            if(prod.isPresent()){
                for(Shop_Products ch : shopProducts){
                    if(ch.getShop().getShopID()==shopID &&
                    ch.getProduct().getProductId()==prodID){
                        prdshpRepo.delete(ch);
                        return true;
                    }
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public Boolean deleteAProductByBarcodeFromAShop(int shopID, String prodBarcode) {
        Optional<product> prod=prdRepo.findByproductBarcode(prodBarcode);
        if(prod.isPresent()) return DeleteAProductFromAShop(shopID,prod.get().getProductId());
        return null;
    }
}

package com.example.GradProJM.Services;

import com.example.GradProJM.Model.*;
import com.example.GradProJM.Repos.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductShopService {
    private final productShopRepository prdshpRepo;
    private final ProductRepository prdRepo;
    private final ShopOwnerRepository shpRepo;
    private final OrderRepository ordRepo;
    private final customerFeedbackRepository custfeedbkRepo;
    private final CustomerRepository custRepo;

    @Autowired
    public ProductShopService(productShopRepository prdshpRepo,
                              ProductRepository prdRepo,
                              ShopOwnerRepository shpRepo,
                              OrderRepository ordRepo,
                              customerFeedbackRepository custfeedbkRepo,
                              CustomerRepository custRepo) {
        this.prdshpRepo=prdshpRepo;
        this.prdRepo=prdRepo;
        this.shpRepo=shpRepo;
        this.ordRepo=ordRepo;
        this.custfeedbkRepo=custfeedbkRepo;
        this.custRepo=custRepo;
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
        Optional<Shop_Products> shsh = prdshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcode(shopProducts.getShop().getShopName(),
                shopProducts.getProduct().getProductBarcode());
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
                shpprdct.setDeleted(false);
                shpprdct.setProductPrice(shopProducts.getProductPrice());
                shpprdct.setProductDescription(shopProducts.getProductDescription());
                shpprdct.setProductPublishDate(String.valueOf(LocalDateTime.now()));
                prdshpRepo.save(shpprdct);
                return true;
            }
            return null;
        }

        }
        if(flag ==1 && shsh.get().isDeleted()){
            shsh.get().setDeleted(false);
            shsh.get().setProductPrice(shopProducts.getProductPrice());
            shsh.get().setQuantity(shopProducts.getQuantity());
            shsh.get().setProductDescription(shopProducts.getProductDescription());
            shsh.get().setProductPublishDate(String.valueOf(LocalDateTime.now()));
            return true;
        }
        return null;
    }

    public List getAllProductsForAShop(int shopID) {
        Optional<List> products = prdshpRepo.findShop_ProductsByShop_ShopIDAndDeletedFalse(shopID);
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

    public Boolean deleteAProductByBarcodeFromAShop(String shopName, String prodBarcode) {
        Optional <ShopOwner> shop = shpRepo.findShopOwnerByShopName(shopName);
        if(shop.isPresent()){
            List<Shop_Products> products = shop.get().getShopProducts();
            Optional<Shop_Products> prod = prdshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcode(
                    shopName,prodBarcode
            );
            if(prod.isPresent()){
            products.remove(prod.get());
            shop.get().setShopProducts(products);
            shpRepo.save(shop.get());
            prod.get().setDeleted(true);
            prdshpRepo.save(prod.get());
            return true;}
            return false;
        }
        return false;
    }

//    public Order MakeNewOrder(Order order) {
//        Order ord=new Order();
//        ord.setCustomer(order.getCustomer());
//        ord.setOrderAdd(order.getOrderAdd());
//        ord.setOrderDate(String.valueOf(LocalDate.now()));
//
//        List<Shop_Products> prdcts = new ArrayList<>();
//        for(int i=0;i<order.getProducts().size();i++){
//            Optional<Shop_Products> shopProduct = prdshpRepo.findShop_ProductsByShop_ShopIDAndProduct_ProductId(
//                    order.getProducts().get(i).getShop().getShopID(),
//                    order.getProducts().get(i).getProduct().getProductId());
//            shopProduct.get().setOrder(order);
//            prdcts.add(shopProduct.get());
//
//        }
//
//        ord.setProducts(prdcts);
//        ordRepo.saveAndFlush(ord);
//
////        prdshpRepo.saveAll(prdcts);
//        return order;
//    }



    public Order MakeNewOrder(Order order) {
        Order ord = new Order();
        ord.setCustomer(order.getCustomer());
        ord.setOrderAdd(order.getOrderAdd());
        ord.setOrderDate(String.valueOf(LocalDate.now()));

//        List<Shop_Products> prdcts = new ArrayList<>();
//        for (Shop_Products shopProduct : order.getProducts()) {
//            Optional<Shop_Products> existingProduct = prdshpRepo.findShop_ProductsByShop_ShopIDAndProduct_ProductId(
//                    shopProduct.getShop().getShopID(),
//                    shopProduct.getProduct().getProductId());
//            if (existingProduct.isPresent()) {
//                Shop_Products persistedProduct = existingProduct.get();
//                persistedProduct.setOrder(ord); // Set the order for the persisted product
//                prdcts.add(persistedProduct);
//            } else {
//                // Handle the case when the product is not found
//                // This could be an error condition or require creating a new product entity
//                // Depending on your application logic
//                System.out.println("kaka");
//            }
        return null;

        }

    public Shop_Products rateAProduct(customerRates custRate) {
        Optional<ShopOwner> shop = shpRepo.findById(custRate.getProducts().getShop().getShopID());
        Optional<product> product = prdRepo.findById(custRate.getProducts().getProduct().getProductId());
        if(shop.isPresent() && product.isPresent()) {
            Optional<Shop_Products> shopProduct = prdshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcode(shop.get().getShopName(), product.get().getProductBarcode());
            if (shopProduct.isPresent()) {
                Optional<product> prod = prdRepo.findByproductBarcode(product.get().getProductBarcode());
                double rt = shopProduct.get().getRate();
                int noRates = shopProduct.get().getNumOfRates();
                rt *= noRates;
                noRates += 1;
                rt += custRate.getRate();
                rt /= noRates;
                DecimalFormat df = new DecimalFormat("#.#");
                rt = Double.parseDouble(df.format(rt));
                shopProduct.get().setRate(rt);
                shopProduct.get().setNumOfRates(noRates);
                shopProduct.get().setRate(rt);
                Optional <Customer> customer = custRepo.findById(custRate.getCustomer().getCustID());
                custRate.setProducts(shopProduct.get());
                custRate.setCustomer(customer.get());
                custfeedbkRepo.save(custRate);
                prdRepo.save(prod.get());
                prdshpRepo.save(shopProduct.get());
                return shopProduct.get();
                //todo: save the customer..
                //todo: send an confirmation link to the email ...
                

            }
        }
            return null;

    }

    public Shop_Products rateAProduct(int custID, String shopName, String prodBarcode, customerRates custRate) {
        Optional<Shop_Products> shopProduct = prdshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcode(shopName, prodBarcode);
        Optional <Customer> customer = custRepo.findById(custID);
        custRate.setCustomer(customer.get());
        custRate.setProducts(shopProduct.get());
        return rateAProduct(custRate);
    }

    public List<Shop_Products> view(int shopID, int pageNum) {
        Optional<List<Shop_Products>> products = prdshpRepo.findShop_ProductsByShop_ShopIDAndDeletedFalse(shopID, PageRequest.of(pageNum,2));
        return products.get();
    }
    public int getShopProductsPagesNum(int shopID){
        long totalProducts = prdshpRepo.countByShopShopIDAndDeletedFalse(shopID);
        return (int) Math.ceil((double) totalProducts / 2);
        //todo: change 2 to 10
    }


    public List<Shop_Products> getProductsbyCategory(String category, int shopID, int pageNum) {
        Optional<List<Shop_Products>> products = prdshpRepo.findShop_ProductsByShop_ShopIDAndProductProductCategoryAndDeletedFalse(shopID, category, PageRequest.of(pageNum,2));
        return products.get();
    }
    public int getCategoriesPagesNum(String cat, int shopID){
        long totalProducts = prdshpRepo.countByShopShopIDAndProductProductCategoryAndDeletedFalse(shopID, cat);
        return (int) Math.ceil((double) totalProducts / 2);
        //todo: change 2 to 10
    }



    public Page<Shop_Products> SortASC(int shopID, int pageNum, String field) {
        Pageable pageable = PageRequest.of(pageNum, 2, Sort.by(Sort.Direction.ASC, field));
        return prdshpRepo.findShop_ProductsByShop_ShopIDAndDeletedFalse(shopID, pageable);
    }

    public Page<Shop_Products> sortDESC(int shopID, int pageNum, String field) {
        Pageable pageable = PageRequest.of(pageNum, 2, Sort.by(Sort.Direction.DESC, field));
        return prdshpRepo.findShop_ProductsByShop_ShopIDAndDeletedFalse(shopID, pageable);
    }

    public Shop_Products updateProduct(Shop_Products product,String shopName, String prodBarcode) {
        Optional<Shop_Products> shopProduct = prdshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcode(shopName, prodBarcode);
        if(shopProduct.isPresent()){
            shopProduct.get().setProductPrice(product.getProductPrice());
            shopProduct.get().setQuantity(product.getQuantity());
            prdshpRepo.save(shopProduct.get());
            return shopProduct.get();
        }
        return null;
    }

    public Shop_Products getProductForAShop(String shopName, String barcode) {
        Optional<Shop_Products> shopProduct = prdshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcode(shopName, barcode);
        return shopProduct.orElse(null);
    }

    public List<Shop_Products> getTrendingProducts() {
        Optional<List<Shop_Products>> trendingProds = prdshpRepo.findTopByNumOfSales();
        return trendingProds.orElse(null);
    }

    public List<Shop_Products> getnewProducts() {
        Optional<List<Shop_Products>> trendingProds = prdshpRepo.findTopByDate();
        return trendingProds.orElse(null);
    }

    public List<Shop_Products> SearchProducts(String prodName, int pageNum) {
        Optional<List<Shop_Products>> products = prdshpRepo.findShop_ProductsByProductProductNameStartingWithAndDeletedFalse(prodName, PageRequest.of(pageNum,2));
        return products.get();
    }

    public int getSearchPagesNum(String prodName) {
        long totalProducts = prdshpRepo.countByProductProductNameStartingWithAndDeletedFalse(prodName);
        return (int) Math.ceil((double) totalProducts / 2);
    }
}

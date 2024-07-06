package com.example.GradProJM.Controller;

import com.example.GradProJM.Model.*;
import com.example.GradProJM.Services.ProductService;
import com.example.GradProJM.Services.ProductShopService;
import com.example.GradProJM.Services.shopOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("productShop")
public class ProductShopController {
    private final ProductShopService prdShopService;
    public ProductShopController(ProductShopService prdShopService) {
        this.prdShopService = prdShopService;
    }
    @GetMapping("/{shopName}/{prodBarcode}")
    public ResponseEntity<Shop_Products> getProductForAShop(@PathVariable("shopName") String shopName,
                                                            @PathVariable("prodBarcode") String barcode){
        Optional<Shop_Products> products= Optional.ofNullable(prdShopService.getProductForAShop(shopName,barcode));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
    @GetMapping("getProductsForaShop/{shopID}")
    public ResponseEntity<List> getAllProductsForAShop(@PathVariable("shopID") int shopID){
        Optional<List> products= Optional.ofNullable(prdShopService.getAllProductsForAShop(shopID));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PostMapping("saveRecommended/{userID}/{shopName}/{prodBarcode}")
    public ResponseEntity<Boolean> SaveRecommended(@PathVariable("userID") int userID,
                                                   @PathVariable("shopName") String shopName,
                                                   @PathVariable("prodBarcode") String prodBarcode){
        Optional<Boolean> products= Optional.ofNullable(prdShopService.SaveRecommended(userID, shopName, prodBarcode));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @GetMapping("getProducts/{shopID}/{pageNum}")
    public ResponseEntity<List<Shop_Products>> Search(@PathVariable("shopID") int shopID,
                                                      @PathVariable("pageNum") int pageNum
    ){
        Optional<List<Shop_Products>> products= Optional.ofNullable(prdShopService.view(shopID, pageNum));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @GetMapping("getAllProductsNumOfPages/{shopID}")
    public int getNumOfPagesForaCategory(
                                         @PathVariable("shopID") int shopID
    ){
        int num= prdShopService.getShopProductsPagesNum(shopID);
        return num;
    }

    @GetMapping("getTrendingProducts")
    public ResponseEntity<List<Shop_Products>> getTrendingProducts(){

        Optional<List<Shop_Products>> products= Optional.ofNullable(prdShopService.getTrendingProducts());
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));

    }

    @GetMapping("getNewProducts")
    public ResponseEntity<List<Shop_Products>> getnewProducts(){

        Optional<List<Shop_Products>> products= Optional.ofNullable(prdShopService.getnewProducts());
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));

    }

    @GetMapping("getProducts/{category}/{shopID}/{pageNum}")
    public ResponseEntity<List<Shop_Products>> getProductsbyCategory(@PathVariable("category") String category,
            @PathVariable("shopID") int shopID, @PathVariable("pageNum") int pageNum
    ){
        Optional<List<Shop_Products>> products= Optional.ofNullable(prdShopService.getProductsbyCategory(category,shopID, pageNum));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @GetMapping("sortCategsasc/{category}/{shopID}/{field}/{pageNum}")
    public ResponseEntity<Page<Shop_Products>> sortCategoryPageASC(@PathVariable("category") String category,
                                                                   @PathVariable("shopID") int shopID,
                                                                   @PathVariable("field") String field,
                                                                   @PathVariable("pageNum") int pageNum
    ){
        Optional<Page<Shop_Products>> products= Optional.ofNullable(prdShopService.sortCategoryPageASC(category, shopID, field, pageNum));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @GetMapping("sortCategsDesc/{category}/{shopID}/{field}/{pageNum}")
    public ResponseEntity<Page<Shop_Products>> sortCategoryPageDESC(@PathVariable("category") String category,
                                                                   @PathVariable("shopID") int shopID,
                                                                    @PathVariable("field") String field,
                                                                   @PathVariable("pageNum") int pageNum
    ){
        Optional<Page<Shop_Products>> products= Optional.ofNullable(prdShopService.sortCategoryPageDESC(category, shopID, field,  pageNum));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }





    @GetMapping("getNumOfPages/{category}/{shopID}")
    public int getNumOfPagesForaCategory(@PathVariable("category") String category,
                                                                     @PathVariable("shopID") int shopID
    ){
        int num= prdShopService.getCategoriesPagesNum(category,shopID);
        return num;
    }

    @GetMapping("sortasc/{shopID}/{field}/{pageNum}")
    public ResponseEntity<Page<Shop_Products>> sortASC(@PathVariable("shopID") int shopID,
                                                       @PathVariable("field") String field,
                                                       @PathVariable("pageNum") int pageNum
    ){
        Optional<Page<Shop_Products>> products= Optional.ofNullable(prdShopService.SortASC(shopID,pageNum,field));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @GetMapping("sortDesc/{shopID}/{field}/{pageNum}")
    public ResponseEntity<Page<Shop_Products>> sortDESC(@PathVariable("shopID") int shopID,
                                                       @PathVariable("field") String field,
                                                       @PathVariable("pageNum") int pageNum
    ){
        Optional<Page<Shop_Products>> products= Optional.ofNullable(prdShopService.sortDESC(shopID,pageNum,field));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PatchMapping("updateProduct/{shopName}/{prodBarcode}")
    public ResponseEntity<Shop_Products> updateProduct(@RequestBody Shop_Products prod, @PathVariable("shopName") String shopName,@PathVariable("prodBarcode") String prodBarcode){
        Optional<Shop_Products> product= Optional.ofNullable(prdShopService.updateProduct(prod, shopName,prodBarcode));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @PostMapping("AddanExistingProductToShop")
    public ResponseEntity<Boolean> AddAnExistingProducttoaShop(@RequestBody Shop_Products shopProducts){
        Optional<Boolean> product= Optional.ofNullable(prdShopService.AddAnExistingProducttoaShop(shopProducts));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(false));
    }


    @PostMapping("AddanExistingProductbyBarcodeToShop")
    public ResponseEntity<Boolean> AddAnExistingProductByBarcodetoaShop(@RequestBody Shop_Products shopProducts){
        Optional<Boolean> product= Optional.ofNullable(prdShopService.AddAnExistingProductByBarcodetoaShop(shopProducts));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FOUND)
                        .body(false));
    }



    @PostMapping("AddanNewProductToaShop")
    public ResponseEntity<Boolean> AddNewProducttoaShop(@RequestBody Shop_Products shopProducts){
        Optional<Boolean> product= Optional.ofNullable(prdShopService.AddNewProducttoaShop(shopProducts));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(false));
    }


    @DeleteMapping("deleteAProductFromAShop/{shopID}/{prodID}")
    public ResponseEntity<Boolean> DeleteAProductFromAShop(@PathVariable("shopID") int shopID,@PathVariable("prodID") int prodID){
        Optional<Boolean> product= Optional.ofNullable(prdShopService.DeleteAProductFromAShop(shopID,prodID));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(false));
    }


    @DeleteMapping("deleteAProductByBarcodeFromAShop/{shopName}/{prodBarcode}")
    public ResponseEntity<Boolean> deleteAProductByBarcodeFromAShop(@PathVariable("shopName") String shopName,@PathVariable("prodBarcode") String prodBarcode){
        Optional<Boolean> product= Optional.ofNullable(prdShopService.deleteAProductByBarcodeFromAShop(shopName,prodBarcode));
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(false));
    }


    @PostMapping("makeNewOrder")
    public ResponseEntity<Order> makeNewOrder(@RequestBody Order order){
        Optional<Order> ord= Optional.ofNullable(prdShopService.MakeNewOrder(order));
        return ord.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PatchMapping("rateAProduct")
    public ResponseEntity<Shop_Products> rateAProduct(@RequestBody customerRates custRate){

//        @PathVariable("custID") int custID,
//        @PathVariable("shopName") String shopName,
//        @PathVariable("prodBarcode") String prodBarcode,
//        @PathVariable("rate") double rate
        Optional<Shop_Products> productRate= Optional.ofNullable(prdShopService.rateAProduct(
                custRate));
        return productRate.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PatchMapping("rateAProduct/{userID}/{shopName}/{prodBarcode}")
    public ResponseEntity<Shop_Products> rateAProduct(@PathVariable("userID") int userID,
                                                      @PathVariable("shopName") String shopName,
                                                      @PathVariable("prodBarcode") String prodBarcode,
                                                      @RequestBody customerRates custRate){


        Optional<Shop_Products> productRate= Optional.ofNullable(prdShopService.rateAProduct(userID,
                shopName,
                prodBarcode,
                custRate));
        return productRate.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
//    @GetMapping("getProductsBySearching/{custID}/{prodName}/{pageNum}")
//    public ResponseEntity<List<Shop_Products>> SearchProducts(@PathVariable("custID") int custID,
//            @PathVariable("prodName") String prodName,
//                                                      @PathVariable("pageNum") int pageNum
//    ){
//        Optional<List<Shop_Products>> products= Optional.ofNullable(prdShopService.SearchProducts(custID,prodName, pageNum));
//        return products.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(null));
//    }
    @GetMapping("getNumOfPagesWhenSearching/{prodName}")
    public int SearchProducts(@PathVariable("prodName") String prodName
    ){
        int num= prdShopService.getSearchPagesNum(prodName);
        return num;
    }


    @GetMapping("SearchByCategory/{Category}/{pageNum}")
    public ResponseEntity<List<Shop_Products>> SearchProductsByCatt(@PathVariable("Category") String Category,
                                                              @PathVariable("pageNum") int pageNum
    ){
        Optional<List<Shop_Products>> products= Optional.ofNullable(prdShopService.SearchProductsByCatt(Category, pageNum));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @GetMapping("getNumOfPagesWhenSearchingByCatteg/{Category}")
    public int getSearchByCattPagesNum(@PathVariable("Category") String Category
    ){
        int num= prdShopService.getSearchByCattPagesNum(Category);
        return num;
    }



    @GetMapping("sortCategoryasc/{category}/{field}/{pageNum}")
    public ResponseEntity<Page<Shop_Products>> sortCatgeoryASC(@PathVariable("category") String category,
                                                       @PathVariable("field") String field,
                                                       @PathVariable("pageNum") int pageNum
    ){
        Optional<Page<Shop_Products>> products= Optional.ofNullable(prdShopService.sortCatgeoryASC(category,pageNum,field));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @GetMapping("sortCategoryDesc/{category}/{field}/{pageNum}")
    public ResponseEntity<Page<Shop_Products>> sortCatgeoryDESC(@PathVariable("category") String category,
                                                               @PathVariable("field") String field,
                                                               @PathVariable("pageNum") int pageNum
    ){
        Optional<Page<Shop_Products>> products= Optional.ofNullable(prdShopService.sortCatgeoryDESC(category,pageNum,field));
        return products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }


    @GetMapping("/recommendations/{productId}")
    public List<Shop_Products> getRecommendations(@PathVariable int productId) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
        return prdShopService.getRecommendations(productId);
    }

//    @GetMapping("/relatedProducts/{custID}/{productId}")
//    public List<Shop_Products> getRelatedProducts(@PathVariable("custID") int custID,
//            @PathVariable("productId") int productId
//                                                  ) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
//        return prdShopService.getRelatedProducts(custID, productId);
//    }

    @GetMapping("/getRelatedProds/{custID}")
    public ResponseEntity<List<Shop_Products>> getRelatedProds(@PathVariable("custID") int custID) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
        Optional<List<Shop_Products>> recommendedProducts = Optional.ofNullable(prdShopService.getRecommendationsBasedOnRecentSearches(custID));

        return recommendedProducts.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PostMapping("/search/{pageNum}")
    public ResponseEntity<List<Shop_Products>> search(@RequestBody SearchAlgo search,
                                      @PathVariable("pageNum") int pageNum) {
        Optional<List<Shop_Products>> products = Optional.ofNullable(prdShopService.searchProducts(search, pageNum));
        return  products.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @GetMapping("/search/numOfPages")
    public Integer NumOfPage(@RequestBody SearchAlgo search) {
        Integer products = prdShopService.NumOfPage(search);
        return products;
    }



}

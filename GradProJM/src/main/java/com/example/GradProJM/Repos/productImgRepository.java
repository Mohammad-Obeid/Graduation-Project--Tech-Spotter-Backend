package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.ProductImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface productImgRepository extends JpaRepository<ProductImageData, Long> {
//    Optional<List<ProductImageData>> findByProduct_ShopShopNameAndProduct_ProductBarcode(String shopName, String barcode);

    Optional <List<ProductImageData>> findProductImageDataByProduct_Product_productBarcodeAndProduct_Shop_ShopName(String barcode,String shopName);

}

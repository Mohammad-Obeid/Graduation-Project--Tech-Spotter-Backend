package com.example.GradProJM.Services;

import com.example.GradProJM.Model.ProductImageData;
import com.example.GradProJM.Model.Shop_Products;
import com.example.GradProJM.Repos.productImgRepository;
import com.example.GradProJM.Repos.productShopRepository;
import com.example.GradProJM.Utils.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class ProductImgService {
    private final productImgRepository repository;
    private final productShopRepository prdshpRepo;

    public ProductImgService(productImgRepository repository, productShopRepository prdshpRepo) {
        this.repository = repository;
        this.prdshpRepo = prdshpRepo;
    }


    public List<ProductImageData> saveImagesForProduct(List<MultipartFile> imageFiles, String shopName, String prodBarcode) throws IOException {
        List<ProductImageData> savedImages = new ArrayList<>();

        Optional<Shop_Products> productOptional = prdshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcodeAndAndDeletedFalse(shopName, prodBarcode);
        if (!productOptional.isPresent()) {
            throw new IllegalArgumentException("Product not found for shopName: " + shopName + " and prodBarcode: " + prodBarcode);
        }

        Shop_Products product = productOptional.get();

        for (MultipartFile imageFile : imageFiles) {
            if (imageFile.isEmpty()) {
                continue;
            }

            ProductImageData imageData = new ProductImageData();
            imageData.setProduct(product);
            imageData.setContentType(imageFile.getContentType());

            byte[] bytes = imageFile.getBytes();
            String base64Encoded = Base64.getEncoder().encodeToString(bytes);
            imageData.setBase64(base64Encoded);

            savedImages.add(imageData);
        }

        repository.saveAll(savedImages);
        return savedImages;
    }


    public  List<String> getImagesForProduct(String prodBarcode, String shopName) {
        List<String> base64Images = new ArrayList<>();
        Optional<Shop_Products> product = prdshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcodeAndAndDeletedFalse(shopName,prodBarcode);
        Optional<List<ProductImageData>> imageList = repository.findProductImageDataByProduct_Product_productBarcodeAndProduct_Shop_ShopName(prodBarcode,shopName);
        for (ProductImageData imageData : imageList.get()) {
            if (imageData.getProduct()==(product.get())) {
                base64Images.add("data:"+imageData.getContentType()+";base64,"+((imageData.getBase64())));
            }
        }
        return base64Images;
    }
}

package com.example.GradProJM.Services;

import com.example.GradProJM.Model.ImageData;
import com.example.GradProJM.Model.ProductImageData;
import com.example.GradProJM.Model.ShopOwner;
import com.example.GradProJM.Model.Shop_Products;
import com.example.GradProJM.Repos.productImgRepository;
import com.example.GradProJM.Repos.productShopRepository;
import com.example.GradProJM.Utils.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ProductImgService {
    private final productImgRepository repository;
    private final productShopRepository prdshpRepo;

    public ProductImgService(productImgRepository repository, productShopRepository prdshpRepo) {
        this.repository = repository;
        this.prdshpRepo = prdshpRepo;
    }
    public String uploadImage(MultipartFile file, String barcode, String shopName) throws IOException {
        Optional<ProductImageData> imgCheck = repository.findByBarcode(barcode);
        if(imgCheck.isPresent()){
            imgCheck.get().setImageData(ImageUtils.compressImage(file.getBytes()));
            repository.save(imgCheck.get());

            Optional<Shop_Products> prod = prdshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcode(shopName,barcode);
            prod.get().setImg(imgCheck.get());
            prdshpRepo.save(prod.get());
            return "Image updated successfully : " + shopName;

        }
        else if(imgCheck.isEmpty()){
            ProductImageData imageData = repository.save(ProductImageData.builder()
                    .barcode(barcode)
                    .type(file.getContentType())
                    .imageData(ImageUtils.compressImage(file.getBytes())).build());
            Optional<Shop_Products> prod = prdshpRepo.findShop_ProductsByShop_ShopNameAndProduct_ProductBarcode(shopName,barcode);
            prod.get().setImg(imageData);
            prdshpRepo.save(prod.get());
            return "file uploaded successfully : " + shopName;
        }
        return null;}

    public byte[] downloadImage(String barcode) {
        Optional<ProductImageData> dbImageData = repository.findByBarcode(barcode);
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
}

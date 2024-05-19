package com.example.GradProJM.Services;
import com.example.GradProJM.Model.ImageData;
import com.example.GradProJM.Model.ShopOwner;
import com.example.GradProJM.Model.product;
import com.example.GradProJM.Repos.ProductRepository;
import com.example.GradProJM.Repos.ShopOwnerRepository;
import com.example.GradProJM.Repos.StorageRepository;
import com.example.GradProJM.Utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class StorageService {

    private final StorageRepository repository;
    private final ShopOwnerRepository shopRepo;

    public StorageService(StorageRepository repository,
                          ShopOwnerRepository shopRepo,
                          ProductRepository prdRepo) {
        this.repository = repository;
        this.shopRepo = shopRepo;
    }


    public String uploadImage(MultipartFile file, String shopName) throws IOException {
        Optional<ImageData> imgCheck = repository.findByName(shopName);
        if(imgCheck.isPresent()){
            imgCheck.get().setImageData(ImageUtils.compressImage(file.getBytes()));
            repository.save(imgCheck.get());

            Optional<ShopOwner> shop = shopRepo.findShopOwnerByShopName(shopName);
            shop.get().setImageData(imgCheck.get());
            shopRepo.save(shop.get());
            return "Image updated successfully : " + shopName;

        }
        else if(imgCheck.isEmpty()){
            ImageData imageData = repository.save(ImageData.builder()
                    .name(shopName)
                    .type(file.getContentType())
                    .imageData(ImageUtils.compressImage(file.getBytes())).build());
            Optional<ShopOwner> shop = shopRepo.findShopOwnerByShopName(shopName);
            shop.get().setImageData(imageData);
            shopRepo.save(shop.get());
            return "file uploaded successfully : " + shopName;
        }


        return null;
    }
//    public String uploadProdImage(List<MultipartFile> files, String prodBarcode) throws IOException {
//        Optional<product> productOpt = prdRepo.findByproductBarcode(prodBarcode);
//        if (!productOpt.isPresent()) {
//            return "Product not found for barcode: " + prodBarcode;
//        }
//        product product = productOpt.get();
//        List<ImageData> images= new ArrayList<>();
//        for (MultipartFile file : files) {
//            System.out.println("jajaj");
//            ImageData image = repository.save(ImageData.builder()
//                    .name(prodBarcode)
//                    .type(file.getContentType())
//                    .imageData(ImageUtils.compressImage(file.getBytes())).build());
//            images.add(image);
////            repository.save(image);
//        }
//        product.setImg(images);
//        prdRepo.save(product);
//
//        return "Files uploaded successfully for barcode: " + prodBarcode;
//
////        return null;
//    }

    public byte[] downloadImage(String fileName) {
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

//    public List<byte[]> downloadProdImage(String prodBarcode) {
//        Optional<List<ImageData>> dbImageData = repository.findImageDataByName(prodBarcode);
//        System.out.println(dbImageData.get().size()+" ////////////////////////////////////////////////////////////////////");
//        ArrayList<byte[]> lst = new ArrayList<>();
//        for(int i = 0; i < dbImageData.get().size();i++) {
//            lst.add(ImageUtils.decompressImage(dbImageData.get().get(i).getImageData()));
////            return lst;
////            lst.add(images);
//        }
//        return lst;
//    }
}

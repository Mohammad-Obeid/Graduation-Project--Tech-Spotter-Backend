package com.example.GradProJM.Services;
import com.example.GradProJM.Model.ImageData;
import com.example.GradProJM.Model.ShopOwner;
import com.example.GradProJM.Repos.ShopOwnerRepository;
import com.example.GradProJM.Repos.StorageRepository;
import com.example.GradProJM.Utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
@Service
public class StorageService {

    private final StorageRepository repository;
    private final ShopOwnerRepository shopRepo;

    public StorageService(StorageRepository repository, ShopOwnerRepository shopRepo) {
        this.repository = repository;
        this.shopRepo = shopRepo;
    }


    public String uploadImage(MultipartFile file, String shopName) throws IOException {
        Optional<ImageData> imgCheck = repository.findByName(shopName);
        if(imgCheck.isPresent()){
//            imgCheck = Optional.of(repository.save(imgCheck.get().builder()
//                    .name(shopName)
//                    .type(file.getContentType())
//                    .imageData(ImageUtils.compressImage(file.getBytes())).build()));
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

    public byte[] downloadImage(String fileName) {
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
}

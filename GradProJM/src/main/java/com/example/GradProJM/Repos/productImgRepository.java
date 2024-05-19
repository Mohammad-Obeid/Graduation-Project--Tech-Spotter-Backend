package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.ProductImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface productImgRepository extends JpaRepository<ProductImageData, Long> {
    Optional<ProductImageData> findByBarcode(String barcode);

}

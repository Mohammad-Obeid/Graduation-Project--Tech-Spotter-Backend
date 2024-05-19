package com.example.GradProJM.Repos;
import com.example.GradProJM.Model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface StorageRepository extends JpaRepository<ImageData,Long> {
    Optional<ImageData> findByName(String fileName);
}
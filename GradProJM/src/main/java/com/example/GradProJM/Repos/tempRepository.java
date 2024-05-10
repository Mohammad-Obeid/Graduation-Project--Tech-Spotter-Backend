package com.example.GradProJM.Repos;

import com.example.GradProJM.Model.tempDataBaseForVerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface tempRepository extends JpaRepository<tempDataBaseForVerificationCode,Integer> {
    Optional<tempDataBaseForVerificationCode> findByUserEmail(String email);
}

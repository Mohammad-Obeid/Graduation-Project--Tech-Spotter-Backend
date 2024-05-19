package com.example.GradProJM.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productImageData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String barcode;
    private String type;
    @Lob
    @Column(name = "imagedata",length = 100000)
    private byte[] imageData;
}

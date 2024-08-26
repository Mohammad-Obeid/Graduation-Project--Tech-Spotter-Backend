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

    @ManyToOne
    private Shop_Products product;
    private String contentType;
    @Lob()
    @Column(length = 1000000000)
    private String base64;
}

package com.example.GradProJM.Model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.NonNull;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchAlgo {
    @NotNull
    private String productName;
    private String productCategory;
    private Integer minPrice, maxPrice;
    private String prodCondition;
    private Integer custID;
    private String sortBy;
    private boolean isAscending;


}

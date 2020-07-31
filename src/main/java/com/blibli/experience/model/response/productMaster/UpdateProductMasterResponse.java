package com.blibli.experience.model.response.productMaster;

import com.blibli.experience.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductMasterResponse {

    private UUID productId;
    private String productName;
    private String productBrand;
    private String productBarcode;
    private ProductCategory productCategory;
    private String productDescription;
    private Double productWeight;
    private String productVolume;
    private List<String> productImagePaths;
    private String qrImagePath;
    private LocalDateTime productCreatedAt;

}

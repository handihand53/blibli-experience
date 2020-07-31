package com.blibli.experience.model.response.product;

import com.blibli.experience.entity.document.ProductStock;
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
public class GetProductMasterDetailWithIdResponse {

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

    private List<ProductStock> productStockList;
}

package com.blibli.experience.model.response.productMaster;

import com.blibli.experience.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostProductMasterResponse {

    @NotBlank
    private String productName;

    private String productBrand;
    private String productBarcode;
    private ProductCategory productCategory;
    private String productDescription;
    private Double productWeight;
    private String productVolume;

}


package com.blibli.experience.model.request.productMaster;

import com.blibli.experience.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductMasterRequest {

    @NotNull
    private UUID productId;

    @NotBlank
    private String productName;

    @NotBlank
    private String productBrand;

    @NotBlank
    private String productBarcode;

    @NotBlank
    private String productDescription;

    @NotBlank
    private String productVolume;

    private ProductCategory productCategory;
    private Double productWeight;
}

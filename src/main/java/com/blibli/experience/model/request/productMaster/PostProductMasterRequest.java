package com.blibli.experience.model.request.productMaster;

import com.blibli.experience.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostProductMasterRequest {

    @NotBlank
    private String productName;

    private String productBrand;
    private String productBarcode;
    private ProductCategory productCategory;
    private String productDescription;
    private Double productWeight;
    private String productVolume;

}

package com.blibli.experience.model.response.productMaster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductMasterMetadataResponse {

    private String productName = "String";
    private String productBrand = "String";
    private String productBarcode = "String";
    private String productDescription = "String";
    private String productVolume = "String";
    private String productWeight = "Double";
    private String productCategory = "ProductCategory Enum";

}

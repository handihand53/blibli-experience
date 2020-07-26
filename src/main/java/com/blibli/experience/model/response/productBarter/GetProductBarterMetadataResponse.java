package com.blibli.experience.model.response.productBarter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductBarterMetadataResponse {

    private String userId = "UUID";
    private String productBarterName = "String";
    private String productBarterBrand = "String";
    private String productBarterDescription = "String";
    private String productBarterVolume = "String";
    private String productBarterPreference = "String";
    private String productBarterPackage = "String";
    private String productBarterWeight = "Double";
    private String productCategory = "ProductCategory Enum";
    private String productBarterCondition = "ProductBarterCondition Enum";

}

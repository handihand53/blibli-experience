package com.blibli.experience.model.response.productBarter;

import com.blibli.experience.enums.ProductBarterCondition;
import com.blibli.experience.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductBarterAvailableResponse {

    private UUID productBarterId;
    private String productBarterName;
    private String productBarterBrand;
    private String productBarterDescription;
    private Double productBarterWeight;
    private String productBarterVolume;
    private ProductCategory productCategory;
    private String productBarterPreference;
    private String productBarterPackage;
    private ProductBarterCondition productBarterCondition;
    private String productBarterImagePaths;
    private LocalDateTime productBarterCreatedAt;

}

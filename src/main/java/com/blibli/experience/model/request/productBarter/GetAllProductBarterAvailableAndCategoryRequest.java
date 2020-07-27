package com.blibli.experience.model.request.productBarter;

import com.blibli.experience.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProductBarterAvailableAndCategoryRequest {

    private ProductCategory productCategory;
    private Integer skipCount;

}

package com.blibli.experience.model.request.product;

import com.blibli.experience.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProductByCategoryRequest {

    private ProductCategory productCategory;
    private Integer skipCount;

}

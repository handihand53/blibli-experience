package com.blibli.experience.model.response.product;

import com.blibli.experience.enums.ProductCategory;
import com.blibli.experience.enums.ProductStatus;
import com.blibli.experience.enums.ProductTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductsWithTagResponse {

  private UUID productId;
  private String productName;
  private Integer productPrice;
  private String productBarcode;
  private List<String> productImagePaths;
  private ProductCategory productCategory;
  private ProductStatus productStatus;
  private List<ProductTag> productTags;

}

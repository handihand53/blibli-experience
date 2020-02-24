package com.blibli.experience.model.response.product;

import com.blibli.experience.entity.form.ShopForm;
import com.blibli.experience.entity.form.UserForm;
import com.blibli.experience.enums.ProductCategory;
import com.blibli.experience.enums.ProductStatus;
import com.blibli.experience.enums.ProductTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductDetailWithIdResponse {

  private String productName;
  private String productPrice;
  private ShopForm productShopForm;
  private UserForm productUserForm;
  private Integer productStock;
  private String productBrand;
  private String productBarcode;
  private ProductCategory productCategory;
  private ProductStatus productStatus;
  private String productDescription;
  private String productWeight;
  private String productVolume;
  private String productAccessory;
  private List<String> productImagePaths;
  private List<ProductTag> productTags;
  private String productCreatedAt;

}

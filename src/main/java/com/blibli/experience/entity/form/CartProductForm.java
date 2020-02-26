package com.blibli.experience.entity.form;

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
public class CartProductForm {

  private UUID productId;
  private String productBarcode;
  private String productName;
  private Integer productPrice;
  private Integer productStock;
  private String productImagePath;
  private ProductStatus productStatus;
  private List<ProductTag> productTags;
  private Integer amount;

}

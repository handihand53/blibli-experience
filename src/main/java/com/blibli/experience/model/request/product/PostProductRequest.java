package com.blibli.experience.model.request.product;

import com.blibli.experience.enums.ProductCategory;
import com.blibli.experience.enums.ProductStatus;
import com.blibli.experience.enums.ProductTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostProductRequest {

  @NotBlank
  private String productName;

  private String productBrand;
  private String productBarcode;
  private String productDescription;
  private Double productWeight;
  private String productVolume;
  private String productAccessory;
  private ProductCategory productCategory;

}

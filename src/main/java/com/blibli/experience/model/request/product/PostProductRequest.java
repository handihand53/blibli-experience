package com.blibli.experience.model.request.product;

import com.blibli.experience.enums.ProductCategory;
import com.blibli.experience.enums.ProductStatus;
import com.blibli.experience.enums.ProductTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostProductRequest {

  private UUID merchantId;

  @NotBlank
  private String name;

  @NotNull
  private Integer price;

  @NotNull
  private Integer stock;

  private String brand;

  private String barcode;

  private String description;

  private Double weight;

  private String volume;

  private ProductStatus productStatus;

  private ProductCategory productCategory;

  private List<ProductTag> tags;

}

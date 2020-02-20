package com.blibli.experience.model.response.product;

import com.blibli.experience.enums.ProductCategory;
import com.blibli.experience.enums.ProductStatus;
import com.blibli.experience.enums.ProductTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductAvailableResponse {

  private UUID id;
  private String name;
  private Integer price;
  private String barcode;
  private List<String> imagePath;
  private ProductCategory category;
  private ProductStatus status;
  private List<ProductTag> tags;
  private LocalDateTime createdAt;

}

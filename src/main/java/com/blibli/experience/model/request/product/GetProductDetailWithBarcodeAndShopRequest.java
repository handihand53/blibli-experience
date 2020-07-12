package com.blibli.experience.model.request.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductDetailWithBarcodeAndShopRequest {

  @NotNull
  private UUID shopId;

  @NotNull
  private String productBarcode;

}

package com.blibli.experience.model.request.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductDetailWithBarcodeAndShopRequest {

  private UUID shopId;
  private String productBarcode;

}

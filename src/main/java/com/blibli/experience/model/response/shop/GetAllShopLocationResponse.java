package com.blibli.experience.model.response.shop;

import com.blibli.experience.entity.form.AddressForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllShopLocationResponse {

  private UUID shopId;
  private String shopName;
  private AddressForm shopAddress;
  private Double[] shopLocation;

}

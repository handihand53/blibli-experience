package com.blibli.experience.entity.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopForm {

  private UUID shopId;
  private String shopName;
  private String shopDescription;
  private AddressForm shopAddress;
  private Double[] shopLocation;

}

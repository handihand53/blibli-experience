package com.blibli.experience.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopDto {

  private UUID shopId;
  private String shopName;
  private String shopDescription;
  private AddressDto shopAddress;
  private Double[] shopLocation;

}

package com.blibli.experience.model.response.shop;

import com.blibli.experience.entity.form.AddressForm;
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
public class GetShopDetailResponse {

  private UUID shopId;
  private String shopName;
  private UUID userId;
  private String shopDescription;
  private AddressForm shopAddress;
  private Double[] shopLocation;
  private LocalDateTime shopCreatedAt;

}

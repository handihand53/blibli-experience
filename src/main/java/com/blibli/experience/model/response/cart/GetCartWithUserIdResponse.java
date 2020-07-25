package com.blibli.experience.model.response.cart;

import com.blibli.experience.entity.form.CartForm;
import com.blibli.experience.entity.form.StockForm;
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
public class GetCartWithUserIdResponse {

  private UUID cartId;
  private UUID userId;
  private List<CartForm> cartForms;
  private LocalDateTime lastUpdated;

}

package com.blibli.experience.model.response.cart;

import com.blibli.experience.entity.form.CartForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCartWithUserIdResponse {

  private UUID cartId;
  private List<CartForm> cartProducts;

}

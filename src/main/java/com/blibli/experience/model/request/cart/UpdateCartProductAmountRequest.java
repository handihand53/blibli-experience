package com.blibli.experience.model.request.cart;

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
public class UpdateCartProductAmountRequest {

    @NotNull
    private UUID cartId;

    @NotNull
    private UUID stockId;

    private Integer amount;

}

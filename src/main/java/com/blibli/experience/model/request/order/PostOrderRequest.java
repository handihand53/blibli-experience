package com.blibli.experience.model.request.order;

import com.blibli.experience.enums.DeliveryType;
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
public class PostOrderRequest {

    @NotNull
    private UUID cartId;

    private DeliveryType deliveryType;

}

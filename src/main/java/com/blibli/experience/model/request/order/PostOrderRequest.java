package com.blibli.experience.model.request.order;

import com.blibli.experience.enums.DeliveryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostOrderRequest {

    @NotNull
    private UUID cartId;

    @NotNull
    private UUID shopId;

    private List<UUID> productStockIdList;
    private DeliveryType deliveryType;

}

package com.blibli.experience.model.request.order;

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
public class UpdateOrderDeliveryReceiptRequest {

    @NotNull
    private UUID orderId;

    private String deliveryReceipt;

}

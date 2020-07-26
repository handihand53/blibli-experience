package com.blibli.experience.model.request.biddingOrder;

import com.blibli.experience.entity.form.ReceiptForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBiddingOrderDeliveryReceiptRequest {

    @NotNull
    private UUID biddingOrderId;

    @NotBlank
    private String deliveryReceipt;

}

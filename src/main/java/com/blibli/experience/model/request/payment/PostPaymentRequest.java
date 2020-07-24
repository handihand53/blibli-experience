package com.blibli.experience.model.request.payment;

import com.blibli.experience.enums.PaymentType;
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
public class PostPaymentRequest {

    @NotNull
    private UUID orderId;

    private PaymentType paymentType;

}

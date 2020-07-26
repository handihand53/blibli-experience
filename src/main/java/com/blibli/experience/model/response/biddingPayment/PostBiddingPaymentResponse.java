package com.blibli.experience.model.response.biddingPayment;

import com.blibli.experience.entity.form.BiddingOrderDataForm;
import com.blibli.experience.enums.PaymentType;
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
public class PostBiddingPaymentResponse {

    private UUID biddingOrderPaymentId;
    private String orderTransactionId;
    private BiddingOrderDataForm biddingOrderDataForm;
    private PaymentType biddingOrderPaymentType;
    private LocalDateTime biddingOrderPaymentCreatedAt;

}

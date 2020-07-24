package com.blibli.experience.model.response.payment;

import com.blibli.experience.entity.form.OrderDataForm;
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
public class PostPaymentResponse {

    private UUID paymentId;
    private String orderTransactionId;
    private OrderDataForm orderDataForm;
    private PaymentType paymentType;
    private LocalDateTime paymentCreatedAt;
}

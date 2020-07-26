package com.blibli.experience.entity.document;

import com.blibli.experience.entity.form.BiddingOrderDataForm;
import com.blibli.experience.entity.form.OrderDataForm;
import com.blibli.experience.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = BiddingPayment.COLLECTION_NAME)
public class BiddingPayment {

    public static final String COLLECTION_NAME = "biddingPayment";
    public static final String ID = "id";
    public static final String ORDER_TRANSACTION = "orderTransactionId";
    public static final String BIDDING_ORDER_DATA_FORM = "biddingOrderDataForm";
    public static final String PAYMENT_TYPE = "paymentType";
    public static final String CREATED_AT = "createdAt";

    @Id
    @Field(value = ID)
    private UUID biddingOrderPaymentId;

    @Field(value = ORDER_TRANSACTION)
    private String orderTransactionId;

    @Field(value = BIDDING_ORDER_DATA_FORM)
    private BiddingOrderDataForm biddingOrderDataForm;

    @Field(value = PAYMENT_TYPE)
    private PaymentType biddingOrderPaymentType;

    @Field(value = CREATED_AT)
    private LocalDateTime biddingOrderPaymentCreatedAt;

}

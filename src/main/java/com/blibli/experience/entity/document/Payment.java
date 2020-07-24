package com.blibli.experience.entity.document;

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
@Document(collection = Payment.COLLECTION_NAME)
public class Payment {

    public static final String COLLECTION_NAME = "payment";
    public static final String ID = "id";
    public static final String ORDER_TRANSACTION = "orderTransactionId";
    public static final String ORDER_DATA_FORM = "orderDataForm";
    public static final String PAYMENT_TYPE = "paymentType";
    public static final String CREATED_AT = "createdAt";

    @Id
    @Field(value = ID)
    private UUID paymentId;

    @Field(value = ORDER_TRANSACTION)
    private String orderTransactionId;

    @Field(value = ORDER_DATA_FORM)
    private OrderDataForm orderDataForm;

    @Field(value = PAYMENT_TYPE)
    private PaymentType paymentType;

    @Field(value = CREATED_AT)
    private LocalDateTime paymentCreatedAt;

}

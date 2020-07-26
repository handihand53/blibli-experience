package com.blibli.experience.entity.document;

import com.blibli.experience.entity.form.*;
import com.blibli.experience.enums.DeliveryType;
import com.blibli.experience.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = Order.COLLECTION_NAME)
public class Order {

    public static final String COLLECTION_NAME = "order";
    public static final String ID = "id";
    public static final String ORDER_TRANSACTION = "orderTransactionId";
    public static final String USER = "user";
    public static final String SHOP = "shop";
    public static final String PRODUCTS = "products";
    public static final String DELIVERY_TYPE = "deliveryType";
    public static final String DELIVERY_RECEIPT = "deliveryReceipt";
    public static final String PAYMENT_ID = "paymentId";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String CREATED_AT = "createdAt";

    @Id
    @Field(value = ID)
    private UUID orderId;

    @Field(value = ORDER_TRANSACTION)
    private String orderTransactionId;

    @Field(value = USER)
    private UserDataForm userDataForm;

    @Field(value = SHOP)
    private ShopForm shopForm;

    @Field(value = PRODUCTS)
    private List<CartForm> cartForms;

    @Field(value = DELIVERY_TYPE)
    private DeliveryType deliveryType;

    @Field(value = DELIVERY_RECEIPT)
    private ReceiptForm deliveryReceipt;

    @Field(value = PAYMENT_ID)
    private UUID paymentId;

    @Field(value = ORDER_STATUS)
    private OrderStatus orderStatus;

    @Field(value = CREATED_AT)
    private LocalDateTime orderCreatedAt;
}

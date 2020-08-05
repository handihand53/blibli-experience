package com.blibli.experience.entity.document;

import com.blibli.experience.entity.dto.*;
import com.blibli.experience.enums.BiddingOrderStatus;
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
@Document(collection = BiddingOrder.COLLECTION_NAME)
public class BiddingOrder {

    public static final String COLLECTION_NAME = "biddingOrder";
    public static final String ID = "id";
    public static final String ORDER_TRANSACTION = "orderTransactionId";
    public static final String BIDDING_OWNER = "biddingOwner";
    public static final String BIDDING_WINNER = "biddingWinner";
    public static final String PRODUCT_BIDDING = "products";
    public static final String DELIVERY_RECEIPT = "deliveryReceipt";
    public static final String PAYMENT_ID = "paymentId";
    public static final String ORDER_STATUS = "biddingOrderStatus";
    public static final String CREATED_AT = "createdAt";

    @Id
    @Field(value = ID)
    private UUID biddingOrderId;

    @Field(value = ORDER_TRANSACTION)
    private String orderTransactionId;

    @Field(value = BIDDING_OWNER)
    private UserDto biddingOwner;

    @Field(value = BIDDING_WINNER)
    private UserDto biddingWinner;

    @Field(value = PRODUCT_BIDDING)
    private ProductBiddingDto productBiddingDto;

    @Field(value = DELIVERY_RECEIPT)
    private ReceiptDto deliveryReceipt;

    @Field(value = PAYMENT_ID)
    private UUID paymentId;

    @Field(value = ORDER_STATUS)
    private BiddingOrderStatus biddingOrderStatus;

    @Field(value = CREATED_AT)
    private LocalDateTime biddingOrderCreatedAt;

}

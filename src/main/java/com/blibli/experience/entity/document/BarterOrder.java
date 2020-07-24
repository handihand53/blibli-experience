package com.blibli.experience.entity.document;

import com.blibli.experience.entity.form.BarterSubmissionDataForm;
import com.blibli.experience.entity.form.ProductBarterDataForm;
import com.blibli.experience.entity.form.ReceiptForm;
import com.blibli.experience.entity.form.UserDataForm;
import com.blibli.experience.enums.BarterItemStatus;
import com.blibli.experience.enums.BarterOrderStatus;
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
@Document(collection = BarterOrder.COLLECTION_NAME)
public class BarterOrder {

    public static final String COLLECTION_NAME = "barterOrder";
    public static final String ID = "id";
    public static final String ORDER_TRANSACTION = "orderTransactionId";
    public static final String SELLING_PRODUCT = "sellingProduct";
    public static final String BUYING_PRODUCT = "buyingProduct";
    public static final String SELLER_FORM = "sellerForm";
    public static final String BUYER_FORM = "buyerForm";
    public static final String SELLER_TO_WAREHOUSE_RECEIPT = "sellerToWarehouseReceipt";
    public static final String BUYER_TO_WAREHOUSE_RECEIPT = "buyerToWarehouseReceipt";
    public static final String WAREHOUSE_TO_SELLER_RECEIPT = "warehouseToSellerReceipt";
    public static final String WAREHOUSE_TO_BUYER_RECEIPT = "warehouseToBuyerReceipt";
    public static final String SELLER_ITEM_STATUS = "sellerItemStatus";
    public static final String BUYER_ITEM_STATUS = "buyerItemStatus";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String CREATED_AT = "createdAt";

    @Id
    @Field(value = ID)
    private UUID barterOrderId;

    @Field(value = ORDER_TRANSACTION)
    private String orderTransactionId;

    @Field(value = SELLING_PRODUCT)
    private ProductBarterDataForm sellingProduct;

    @Field(value = BUYING_PRODUCT)
    private BarterSubmissionDataForm buyingProduct;

    @Field(value = SELLER_FORM)
    private UserDataForm sellerData;

    @Field(value = BUYER_FORM)
    private UserDataForm buyerData;

    @Field(value = SELLER_TO_WAREHOUSE_RECEIPT)
    private ReceiptForm sellerToWarehouseReceipt;

    @Field(value = BUYER_TO_WAREHOUSE_RECEIPT)
    private ReceiptForm buyerToWarehouseReceipt;

    @Field(value = WAREHOUSE_TO_SELLER_RECEIPT)
    private ReceiptForm warehouseToSellerReceipt;

    @Field(value = WAREHOUSE_TO_BUYER_RECEIPT)
    private ReceiptForm warehouseToBuyerReceipt;

    @Field(value = SELLER_ITEM_STATUS)
    private BarterItemStatus sellerItemStatus;

    @Field(value = BUYER_ITEM_STATUS)
    private BarterItemStatus buyerItemStatus;

    @Field(value = ORDER_STATUS)
    private BarterOrderStatus orderStatus;

    @Field(value = CREATED_AT)
    private LocalDateTime barterOrderCreatedAt;

}

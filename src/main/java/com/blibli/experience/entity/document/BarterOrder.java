package com.blibli.experience.entity.document;

import com.blibli.experience.entity.form.UserDataForm;
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
    public static final String SELLING_PRODUCT = "sellingProduct";
    public static final String BUYING_PRODUCT = "buyingProduct";
    public static final String SELLER_FORM = "sellerForm";
    public static final String BUYER_FORM = "buyerForm";
    public static final String SELLER_TO_WAREHOUSE_RECEIPT = "sellerToWarehouseReceipt";
    public static final String BUYER_TO_WAREHOUSE_RECEIPT = "buyerToWarehouseReceipt";
    public static final String WAREHOUSE_TO_SELLER_RECEIPT = "warehouseToSellerReceipt";
    public static final String WAREHOUSE_TO_BUYER_RECEIPT = "warehouseToBuyerReceipt";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String CREATED_AT = "createdAt";

    @Id
    @Field
    private UUID barterOrderId;

    @Field(value = SELLING_PRODUCT)
    private ProductBarter sellingProduct;

    @Field(value = BUYING_PRODUCT)
    private ProductBarter buyingProduct;

    @Field(value = SELLER_FORM)
    private UserDataForm sellerData;

    @Field(value = BUYER_FORM)
    private UserDataForm buyerData;

    @Field(value = SELLER_TO_WAREHOUSE_RECEIPT)
    private String sellerToWarehouseReceipt;

    @Field(value = BUYER_TO_WAREHOUSE_RECEIPT)
    private String buyerToWarehouseReceipt;

    @Field(value = WAREHOUSE_TO_SELLER_RECEIPT)
    private String warehouseToSellerReceipt;

    @Field(value = WAREHOUSE_TO_BUYER_RECEIPT)
    private String warehouseToBuyerReceipt;

    @Field(value = ORDER_STATUS)
    private BarterOrderStatus orderStatus;

    @Field(value = CREATED_AT)
    private LocalDateTime barterOrderCreatedAt;

}

package com.blibli.experience.entity.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = ProductBidding.COLLECTION_NAME)
public class ProductBidding {

    public static final String COLLECTION_NAME = "productBarter";
    public static final String ID = "id";
    public static final String USER = "user";
    public static final String NAME = "name";
    public static final String BRAND = "brand";
    public static final String DESCRIPTION = "description";
    public static final String WEIGHT = "weight";
    public static final String VOLUME = "volume";
    public static final String PACKAGE = "package";
    public static final String START_PRICE = "startPrice";
    public static final String NEXT_BID = "nextBid";
    public static final String CONDITION = "condition";
    public static final String IMAGE_PATH = "imagePath";
    public static final String AVAILABLE_STATUS = "availableStatus";
    public static final String CLOSE_BID_DATE = "closeBidDate";
    public static final String CREATED_AT = "createdAt";

}

package com.blibli.experience.entity.document;

import com.blibli.experience.entity.form.BiddingForm;
import com.blibli.experience.entity.form.UserDataForm;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.enums.ProductBiddingAvailableStatus;
import com.blibli.experience.enums.ProductBiddingCondition;
import com.blibli.experience.enums.ProductCategory;
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
@Document(collection = ProductBidding.COLLECTION_NAME)
public class ProductBidding {

    public static final String COLLECTION_NAME = "productBidding";
    public static final String ID = "id";
    public static final String USER = "user";
    public static final String NAME = "name";
    public static final String BRAND = "brand";
    public static final String DESCRIPTION = "description";
    public static final String WEIGHT = "weight";
    public static final String VOLUME = "volume";
    public static final String PACKAGE = "package";
    public static final String CATEGORY = "category";
    public static final String START_PRICE = "startPrice";
    public static final String CURRENT_PRICE = "currentPrice";
    public static final String NEXT_BID = "nextBid";
    public static final String BIDDING_LIST = "biddingList";
    public static final String CONDITION = "condition";
    public static final String IMAGE_PATH = "imagePath";
    public static final String AVAILABLE_STATUS = "availableStatus";
    public static final String CLOSE_BID_DATE = "closeBidDate";
    public static final String LAST_BID = "lastBid";
    public static final String CREATED_AT = "createdAt";

    @Id
    @Field(value = ID)
    private UUID productBiddingId;

    @Field(value = USER)
    private UserDataForm userData;

    @Field(value = NAME)
    private String productBiddingName;

    @Field(value = BRAND)
    private String productBiddingBrand;

    @Field(value = DESCRIPTION)
    private String productBiddingDescription;

    @Field(value = WEIGHT)
    private Double productBiddingWeight;

    @Field(value = VOLUME)
    private String productBiddingVolume;

    @Field(value = PACKAGE)
    private String productBiddingPackage;

    @Field(value = CATEGORY)
    private ProductCategory productCategory;

    @Field(value = START_PRICE)
    private Integer startPrice;

    @Field(value = CURRENT_PRICE)
    private Integer currentPrice;

    @Field(value = NEXT_BID)
    private Integer nextBid;

    @Field(value = BIDDING_LIST)
    private List<BiddingForm> biddingForms;

    @Field(value = CONDITION)
    private ProductBiddingCondition productBiddingCondition;

    @Field(value = IMAGE_PATH)
    private List<String> productBiddingImagePaths;

    @Field(value = AVAILABLE_STATUS)
    private ProductBiddingAvailableStatus availableStatus;

    @Field(value = CLOSE_BID_DATE)
    private LocalDateTime closeBidDate;

    @Field(value = LAST_BID)
    private LocalDateTime lastBidDate;

    @Field(value = CREATED_AT)
    private LocalDateTime productBiddingCreatedAt;

}

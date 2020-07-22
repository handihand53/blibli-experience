package com.blibli.experience.entity.document;

import com.blibli.experience.entity.form.UserDataForm;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.enums.ProductBarterCondition;
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
@Document(collection = ProductBarter.COLLECTION_NAME)
public class ProductBarter {

    public static final String COLLECTION_NAME = "productBarter";
    public static final String ID = "id";
    public static final String USER = "user";
    public static final String NAME = "name";
    public static final String BRAND = "brand";
    public static final String DESCRIPTION = "description";
    public static final String WEIGHT = "weight";
    public static final String VOLUME = "volume";
    public static final String CATEGORY = "category";
    public static final String PREFERENCE = "preference";
    public static final String PACKAGE = "package";
    public static final String CONDITION = "condition";
    public static final String IMAGE_PATH = "imagePath";
    public static final String AVAILABLE_STATUS = "availableStatus";
    public static final String CREATED_AT = "createdAt";

    @Id
    @Field(value = ID)
    private UUID productBarterId;

    @Field(value = USER)
    private UserDataForm userData;

    @Field(value = NAME)
    private String productBarterName;

    @Field(value = BRAND)
    private String productBarterBrand;

    @Field(value = DESCRIPTION)
    private String productBarterDescription;

    @Field(value = WEIGHT)
    private Double productBarterWeight;

    @Field(value = VOLUME)
    private String productBarterVolume;

    @Field(value = CATEGORY)
    private ProductCategory productCategory;

    @Field(value = PREFERENCE)
    private String productBarterPreference;

    @Field(value = PACKAGE)
    private String productBarterPackage;

    @Field(value = CONDITION)
    private ProductBarterCondition productBarterCondition;

    @Field(value = IMAGE_PATH)
    private List<String> productBarterImagePaths;

    @Field(value = AVAILABLE_STATUS)
    private ProductAvailableStatus availableStatus;

    @Field(value = CREATED_AT)
    private LocalDateTime productBarterCreatedAt;

}

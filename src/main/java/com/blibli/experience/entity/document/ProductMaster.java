package com.blibli.experience.entity.document;

import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
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
@Document(collection = ProductMaster.COLLECTION_NAME)
public class ProductMaster {

    public static final String COLLECTION_NAME = "productMaster";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String BRAND = "brand";
    public static final String BARCODE = "barcode";
    public static final String DESCRIPTION = "description";
    public static final String WEIGHT = "weight";
    public static final String VOLUME = "volume";
    public static final String CATEGORY = "category";
    public static final String IMAGE_PATH = "imagePath";
    public static final String QR_PATH = "qrPath";
    public static final String AVAILABLE_STATUS = "availableStatus";
    public static final String CREATED_AT = "createdAt";

    @Id
    @Field(value = ID)
    private UUID productId;

    @Field(value = NAME)
    @Length(max = 254)
    private String productName;

    @Field(value = BRAND)
    private String productBrand;

    @Field(value = BARCODE)
    @Length(max = 100)
    private String productBarcode;

    @Field(value = CATEGORY)
    private ProductCategory productCategory;

    @Field(value = DESCRIPTION)
    private String productDescription;

    @Field(value = WEIGHT)
    private Double productWeight;

    @Field(value = VOLUME)
    private String productVolume;

    @Field(value = AVAILABLE_STATUS)
    private ProductAvailableStatus availableStatus;

    @Field(value = IMAGE_PATH)
    private List<String> productImagePaths;

    @Field(value = QR_PATH)
    private String qrImagePath;

    @Field(value = CREATED_AT)
    private LocalDateTime productCreatedAt;

}

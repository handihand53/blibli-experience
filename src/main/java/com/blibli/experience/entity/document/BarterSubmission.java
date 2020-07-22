package com.blibli.experience.entity.document;

import com.blibli.experience.entity.form.ProductBarterDataForm;
import com.blibli.experience.entity.form.UserDataForm;
import com.blibli.experience.enums.ProductBarterCondition;
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
@Document(collection = BarterSubmission.COLLECTION_NAME)
public class BarterSubmission {

    public static final String COLLECTION_NAME = "barterSubmission";
    public static final String ID = "id";
    public static final String USER = "user";
    public static final String NAME = "name";
    public static final String BRAND = "brand";
    public static final String DESCRIPTION = "description";
    public static final String WEIGHT = "weight";
    public static final String VOLUME = "volume";
    public static final String PACKAGE = "package";
    public static final String CONDITION = "condition";
    public static final String IMAGE_PATH = "imagePath";
    public static final String TARGET_BARTER = "targetBarter";
    public static final String CREATED_AT = "createdAt";

    @Id
    @Field(value = ID)
    private UUID barterSubmissionId;

    @Field(value = USER)
    private UserDataForm userData;

    @Field(value = NAME)
    private String barterSubmissionName;

    @Field(value = BRAND)
    private String barterSubmissionBrand;

    @Field(value = DESCRIPTION)
    private String barterSubmissionDescription;

    @Field(value = WEIGHT)
    private Double barterSubmissionWeight;

    @Field(value = VOLUME)
    private String barterSubmissionVolume;

    @Field(value = PACKAGE)
    private String barterSubmissionPackage;

    @Field(value = CONDITION)
    private ProductBarterCondition barterSubmissionCondition;

    @Field(value = IMAGE_PATH)
    private List<String> barterSubmissionImagePaths;

    @Field(value = TARGET_BARTER)
    private ProductBarterDataForm barterSubmissionTargetBarter;

    @Field(value = CREATED_AT)
    private LocalDateTime barterSubmissionCreatedAt;

}

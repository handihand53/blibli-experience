package com.blibli.experience.entity.document;

import com.blibli.experience.entity.form.AddressForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = Shop.COLLECTION_NAME)
public class Shop {

    public static final String COLLECTION_NAME = "shop";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String USER_ID = "userId";
    public static final String DESCRIPTION = "description";
    public static final String ADDRESS = "address";
    public static final String LOCATION = "location";
    public static final String CREATED_AT = "createdAt";

    @Id
    @Field(value = ID)
    private UUID shopId;

    @Field(value = NAME)
    @Indexed(unique = true)
    @Length(max = 100)
    private String shopName;

    @Field(value = USER_ID)
    @Indexed(unique = true)
    private UUID userId;

    @Field(value = DESCRIPTION)
    private String shopDescription;

    @Field(value = ADDRESS)
    private AddressForm shopAddress;

    @GeoSpatialIndexed(name = LOCATION)
    private Double[] shopLocation;

    @Field(value = CREATED_AT)
    private LocalDateTime shopCreatedAt;

}

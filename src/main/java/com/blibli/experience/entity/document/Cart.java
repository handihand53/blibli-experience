package com.blibli.experience.entity.document;

import com.blibli.experience.entity.form.CartForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = Cart.COLLECTION_NAME)
public class Cart {

    public static final String COLLECTION_NAME = "cart";
    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String PRODUCTS = "products";
    public static final String CREATED_AT = "createdAt";
    public static final String LAST_UPDATED = "lastUpdated";

    @Id
    @Field(value = ID)
    private UUID cartId;

    @Field(value = USER_ID)
    @Indexed(unique = true)
    private UUID userId;

    @Field(value = PRODUCTS)
    private List<CartForm> cartForms;

    @Field(value = CREATED_AT)
    private LocalDateTime createdAt;

    @Field(value = LAST_UPDATED)
    private LocalDateTime lastUpdated;

}

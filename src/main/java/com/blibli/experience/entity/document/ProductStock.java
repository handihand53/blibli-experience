package com.blibli.experience.entity.document;

import com.blibli.experience.entity.form.ProductDataForm;
import com.blibli.experience.entity.form.ShopForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = ProductStock.COLLECTION_NAME)
@CompoundIndexes({
        @CompoundIndex(name = "stock_product",
                def = "{'shop' : 1, 'product' : 1}",
                unique = true)
})
public class ProductStock {

    public static final String COLLECTION_NAME = "productStock";
    public static final String ID = "id";
    public static final String SHOP = "shop";
    public static final String PRODUCT = "product";
    public static final String STOCK = "stock";
    public static final String PRICE = "price";
    public static final String CREATED_AT = "createdAt";

    @Id
    @Field(value = ID)
    private UUID stockId;

    @Field(value = SHOP)
    private ShopForm shopForm;

    @Field(value = PRODUCT)
    private ProductDataForm productDataForm;

    @Field(value = STOCK)
    private Integer productStock;

    @Field(value = PRICE)
    private Integer productPrice;

    @Field(value = CREATED_AT)
    private LocalDateTime stockCreatedAt;
}

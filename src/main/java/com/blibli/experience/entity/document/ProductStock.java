package com.blibli.experience.entity.document;

import com.blibli.experience.entity.form.ShopForm;
import com.blibli.experience.entity.form.UserForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = ProductStock.COLLECTION_NAME)
public class ProductStock {

    public static final String COLLECTION_NAME = "productStock";
    public static final String ID = "id";
    public static final String USER = "user";
    public static final String SHOP = "shop";
    public static final String STOCK = "stock";
    public static final String PRICE = "price";

    @Field(value = ID)
    private UUID stockId;

    @Field(value = SHOP)
    private ShopForm productShopForm;

    @Field(value = USER)
    private UserForm productUserForm;

    @Field(value = STOCK)
    private Integer productStock;

    @Field(value = PRICE)
    private Integer productPrice;
}

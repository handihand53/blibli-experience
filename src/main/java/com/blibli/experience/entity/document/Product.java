package com.blibli.experience.entity.document;

import com.blibli.experience.entity.form.ShopForm;
import com.blibli.experience.entity.form.UserForm;
import com.blibli.experience.enums.ProductCategory;
import com.blibli.experience.enums.ProductStatus;
import com.blibli.experience.enums.ProductTag;
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
@Document(collection = Product.COLLECTION_NAME)
public class Product {

  public static final String COLLECTION_NAME = "product";
  public static final String ID = "id";
  public static final String NAME = "name";
  public static final String PRICE = "price";
  public static final String SHOP = "shop";
  public static final String USER = "user";
  public static final String BRAND = "brand";
  public static final String BARCODE = "barcode";
  public static final String DESCRIPTION = "description";
  public static final String STOCK = "stock";
  public static final String BID_PRICE = "bidPrice";
  public static final String WEIGHT = "weight";
  public static final String VOLUME = "volume";
  public static final String STATUS = "status";
  public static final String CATEGORY = "category";
  public static final String IMAGE_PATH = "imagePath";
  public static final String TAG = "tag";
  public static final String CREATED_AT = "createdAt";

  @Id
  @Field(value = ID)
  private UUID productId;

  @Field(value = NAME)
  @Length(max = 254)
  private String productName;

  @Field(value = PRICE)
  private Integer productPrice;

  @Field(value = SHOP)
  private ShopForm productShopForm;

  @Field
  private UserForm productUserForm;

  @Field(value = STOCK)
  private Integer productStock;

  @Field(value = BRAND)
  private String productBrand;

  @Field(value = BARCODE)
  @Length(max = 100)
  private String productBarcode;

  @Field(value = DESCRIPTION)
  private String productDescription;

  @Field(value = BID_PRICE)
  private Integer productBidPrice;

  @Field(value = WEIGHT)
  private Double productWeight;

  @Field(value = VOLUME)
  private String productVolume;

  @Field(value = STATUS)
  private ProductStatus productStatus;

  @Field(value = CATEGORY)
  private ProductCategory productCategory;

  @Field(value = IMAGE_PATH)
  private List<String> productImagePaths;

  @Field(value = TAG)
  private List<ProductTag> productTags;

  @Field(value = CREATED_AT)
  private LocalDateTime productCreatedAt;

}
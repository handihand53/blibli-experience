package com.blibli.experience.entity.document;

import com.blibli.experience.entity.form.ShopForm;
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
  public static final String MERCHANT = "merchant";
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
  private UUID id;

  @Field(value = NAME)
  @Length(max = 254)
  private String name;

  @Field(value = PRICE)
  private Integer price;

  @Field(value = MERCHANT)
  private ShopForm shop;

  @Field(value = STOCK)
  private Integer stock;

  @Field(value = BRAND)
  private String brand;

  @Field(value = BARCODE)
  @Length(max = 100)
  private String barcode;

  @Field(value = DESCRIPTION)
  private String description;

  @Field(value = BID_PRICE)
  private Integer bidPrice;

  @Field(value = WEIGHT)
  private Double weight;

  @Field(value = VOLUME)
  private String volume;

  @Field(value = STATUS)
  private ProductStatus status;

  @Field(value = CATEGORY)
  private ProductCategory category;

  @Field(value = IMAGE_PATH)
  private List<String> imagePaths;

  @Field(value = TAG)
  private List<ProductTag> tags;

  @Field(value = CREATED_AT)
  private LocalDateTime createdAt;

}

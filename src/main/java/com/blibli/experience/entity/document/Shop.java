package com.blibli.experience.entity.document;

import com.blibli.experience.entity.form.AddressForm;
import com.blibli.experience.enums.ShopTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
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
@Document(collection = Shop.COLLECTION_NAME)
public class Shop {

  public static final String COLLECTION_NAME = "shop";
  public static final String ID = "id";
  public static final String NAME = "name";
  public static final String DESCRIPTION = "description";
  public static final String ADDRESS = "address";
  public static final String TAG = "tag";
  public static final String CREATED_AT = "createdAt";

  @Id
  @Field(value = ID)
  private UUID shopId;

  @Field(value = NAME)
  @Indexed(unique = true)
  @Length(max = 100)
  private String shopName;

  @Field(value = DESCRIPTION)
  private String shopDescription;

  @Field(value = ADDRESS)
  private AddressForm shopAddress;

  @Field(value = TAG)
  private List<ShopTag> shopTags;

  @Field(value = CREATED_AT)
  private LocalDateTime shopCreatedAt;

}
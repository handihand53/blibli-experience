package com.blibli.experience.entity.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = Cart.COLLECTION_NAME)
public class Cart {

  public static final String COLLECTION_NAME = "cart";
  public static final String ID = "id";
  public static final String USER_ID = "userId";

}

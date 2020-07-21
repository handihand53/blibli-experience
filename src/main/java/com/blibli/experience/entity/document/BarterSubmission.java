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
@Document(collection = BarterSubmission.COLLECTION_NAME)
public class BarterSubmission {

    public static final String COLLECTION_NAME = "productBarter";
    public static final String ID = "id";
    public static final String USER = "user";
    public static final String NAME = "name";
    public static final String BRAND = "brand";
    public static final String DESCRIPTION = "description";

}

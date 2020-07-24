package com.blibli.experience.model.response.barterOrder;

import com.blibli.experience.entity.form.BarterSubmissionDataForm;
import com.blibli.experience.entity.form.ProductBarterDataForm;
import com.blibli.experience.entity.form.UserDataForm;
import com.blibli.experience.enums.BarterOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostBarterOrderResponse {

    private UUID barterOrderId;
    private String orderTransactionId;
    private ProductBarterDataForm sellingProduct;
    private BarterSubmissionDataForm buyingProduct;
    private UserDataForm sellerData;
    private UserDataForm buyerData;
    private BarterOrderStatus orderStatus;
    private LocalDateTime barterOrderCreatedAt;
}

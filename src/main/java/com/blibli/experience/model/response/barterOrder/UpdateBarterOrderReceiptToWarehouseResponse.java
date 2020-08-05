package com.blibli.experience.model.response.barterOrder;

import com.blibli.experience.entity.dto.BarterSubmissionDto;
import com.blibli.experience.entity.dto.ProductBarterDto;
import com.blibli.experience.entity.dto.ReceiptDto;
import com.blibli.experience.entity.dto.UserDto;
import com.blibli.experience.enums.BarterItemStatus;
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
public class UpdateBarterOrderReceiptToWarehouseResponse {

    private UUID barterOrderId;
    private String orderTransactionId;
    private ProductBarterDto sellingProduct;
    private BarterSubmissionDto buyingProduct;
    private UserDto sellerData;
    private UserDto buyerData;
    private ReceiptDto sellerToWarehouseReceipt;
    private ReceiptDto buyerToWarehouseReceipt;
    private BarterItemStatus sellerItemStatus;
    private BarterItemStatus buyerItemStatus;
    private BarterOrderStatus orderStatus;
    private LocalDateTime barterOrderCreatedAt;

}

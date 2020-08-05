package com.blibli.experience.model.response.biddingOrder;

import com.blibli.experience.entity.dto.ProductBiddingDto;
import com.blibli.experience.entity.dto.ReceiptDto;
import com.blibli.experience.entity.dto.UserDto;
import com.blibli.experience.enums.BiddingOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBiddingOrderToConfirmationResponse {

    private UUID biddingOrderId;
    private String orderTransactionId;
    private UserDto biddingOwner;
    private UserDto biddingWinner;
    private ProductBiddingDto productBiddingDto;
    private ReceiptDto deliveryReceipt;
    private UUID paymentId;
    private BiddingOrderStatus biddingOrderStatus;

}

package com.blibli.experience.model.response.biddingOrder;

import com.blibli.experience.entity.form.BiddingForm;
import com.blibli.experience.entity.form.ProductBiddingForm;
import com.blibli.experience.entity.form.ReceiptForm;
import com.blibli.experience.entity.form.UserDataForm;
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
public class GetBiddingOrderDetailResponse {

    private UUID biddingOrderId;
    private String orderTransactionId;
    private UserDataForm biddingOwner;
    private UserDataForm biddingWinner;
    private ProductBiddingForm productBiddingForm;
    private ReceiptForm deliveryReceipt;
    private UUID paymentId;
    private BiddingOrderStatus biddingOrderStatus;

}

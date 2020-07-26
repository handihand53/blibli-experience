package com.blibli.experience.entity.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BiddingOrderDataForm {

    private UUID biddingOrderId;
    private String orderTransactionId;
    private UserDataForm biddingOwner;
    private UserDataForm biddingWinner;
    private BiddingForm biddingForm;

}

package com.blibli.experience.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BiddingOrderDto {

    private UUID biddingOrderId;
    private String orderTransactionId;
    private UserDto biddingOwner;
    private UserDto biddingWinner;
    private BiddingDto biddingDto;

}

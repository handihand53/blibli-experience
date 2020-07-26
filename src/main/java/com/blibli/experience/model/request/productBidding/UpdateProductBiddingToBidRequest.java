package com.blibli.experience.model.request.productBidding;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductBiddingToBidRequest {

    @NotNull
    private UUID productBiddingId;

    @NotNull
    private UUID userId;

    private Integer bid;

}
